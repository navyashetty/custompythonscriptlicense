import com.atlassian.jira.bc.user.UserService
import com.atlassian.jira.user.util.UserManager
import com.atlassian.jira.component.ComponentAccessor
import com.atlassian.jira.issue.MutableIssue
import com.atlassian.jira.issue.customfields.option.Option
import com.atlassian.jira.user.ApplicationUser
import com.onresolve.scriptrunner.ldap.LdapUtil
import org.springframework.ldap.core.AttributesMapper
import com.atlassian.crowd.embedded.impl.ImmutableGroup
import javax.naming.directory.SearchControls

//Current issue object
MutableIssue currentIssue = issue
log.warn("Issue:= " + currentIssue.getKey())

def loggedInUser = ComponentAccessor.jiraAuthenticationContext.loggedInUser
def groupManager = ComponentAccessor.groupManager
def customFieldManager = ComponentAccessor.customFieldManager
def userService = ComponentAccessor.getComponent(UserService)
def userManager = ComponentAccessor.getComponent(UserManager)
def commentManager = ComponentAccessor.commentManager

def customFieldObjects = customFieldManager.getCustomFieldObjects(currentIssue)
def cfUserType = customFieldObjects.find { it.name == "User Type" }
def cfVendorEmail  = customFieldObjects.find { it.name == "Vendor Email" }
def cfVendorName = customFieldObjects.find { it.name == "Vendor Name" }
def cfEmailIds = customFieldObjects.find { it.name == "Email IDs" }
def cfUserGroups = customFieldObjects.find { it.name == "User Groups" }

def userType = currentIssue.getCustomFieldValue(cfUserType) as Option
def cfUserGroupsValues = currentIssue.getCustomFieldValue(cfUserGroups) as List

class Employee {

    String employeeId
    String emailId
    String title
    String department
    String displayName

}

/*
   To read employee details from LDAP,
   Make sure the LDAP connection created with name as 'AD_CONNECTION'
   https://scriptrunner.adaptavist.com/5.7.0/confluence/resources.html#_ldap_connection
*/
def employeeDetails = { def cn, def userCN ->
    def personsList = LdapUtil.withTemplate('AD_CONNECTION') { template ->
        template.search("", "(${cn}=${userCN})", SearchControls.SUBTREE_SCOPE, { attributes ->
            try {
                def employee = new Employee()
                employee.setDisplayName(attributes.get('displayName').get() as String)
                employee.setDepartment(attributes.get('department').get() as String)
                employee.setTitle(attributes.get('title').get() as String)
                employee.setEmployeeId(attributes.get('sAMAccountName').get() as String)
                employee.setEmailId(attributes.get('mail').get() as String)
                return employee
            } catch (Exception) {
                log.warn("User ${userCN} not found in internal Active Directory")
            }
        } as AttributesMapper<Employee>)
    }

    return personsList
}

/*  add comment */
def addComment = { String comment ->
    commentManager.create(currentIssue, loggedInUser, comment, true)
}

/*  create user account */
def createUser = { long directoryId, String email, String displayName ->
    //validate user creation
    final UserService.CreateUserRequest createUserRequest = UserService.CreateUserRequest
            .withUserDetails(loggedInUser, email.toLowerCase(), "", email, displayName)
            .inDirectory(directoryId)
            .sendNotification(false)
    UserService.CreateUserValidationResult validationResult = userService.validateCreateUser(createUserRequest)
    if (validationResult.isValid()) {
        ApplicationUser newUser = userService.createUser(validationResult)
        addComment("${email} account created sucessfully..")
        log.warn("${email} account created sucessfully..")
        return newUser
    } else {
        addComment("${email} account already exists. User is added to the requested group[s]")
        log.warn("${email} account already exists")
        ApplicationUser newUser = userManager.findUserInDirectory(email, directoryId)
        return newUser
    }
}

/* add user to group */
def addUserToGroups = { ApplicationUser user, List groupNames ->
    for (groupName in groupNames) {
        //Group group = groupManager.getGroup(groupName.trim())
        if (groupName) {
            groupManager.addUserToGroup(user, groupName as ImmutableGroup)
        } else {
            log.warn("Group Not found ${groupName}")
        }
    }
}

/* =================================================================================
 This script is using to create JIra user accounts
 ================================================================================= */
/* get employee details from LDAP with employee ID */
//def assigneeDetails = employeeDetails( "sAMAccountName", employeeId)

if (userType?.value?.equalsIgnoreCase("Vendor User")) {
    def directoryId = 1 // internal directory id
    def vendorEmail = currentIssue.getCustomFieldValue(cfVendorEmail) as String
    def vendorName = currentIssue.getCustomFieldValue(cfVendorName) as String
    ApplicationUser newUserAccount = createUser (directoryId, vendorEmail, vendorName)
    log.warn("Vendor User:= " + vendorEmail )
    if (newUserAccount) {
        log.warn("Adding Vendor User to the requested group[s]")
        addUserToGroups(newUserAccount, cfUserGroupsValues)
    }
} else if (userType?.value?.equalsIgnoreCase("Internal WDC User")) {
    def directoryId = 10000 // AD authentication directory id
    def emailIds = currentIssue.getCustomFieldValue(cfEmailIds) as String
    if (emailIds) {
        def emailIdsList = emailIds.replaceAll("[\n, ]+", ",").split(",")
        for (String emailId : emailIdsList) {
            def trimmedEmailId = emailId.trim()
            log.warn("Internal WDC User:= " + trimmedEmailId )
            def userDetailsList = employeeDetails("userPrincipalName", trimmedEmailId)
            def userDetails = userDetailsList.get(0) as Employee
            if (userDetails) {
                log.warn("Fetched values are ${userDetails.emailId} ${userDetails.displayName}")
                ApplicationUser newUserAccount = createUser(directoryId, userDetails.emailId, userDetails.displayName)
                if (newUserAccount) {
                    log.warn("Adding Internal WDC user to the requested group[s]")
                    addUserToGroups(newUserAccount, cfUserGroupsValues)
                }
            } else {
                log.warn("Not able to fetch values for ${trimmedEmailId}")
            }
        }
    }
}
