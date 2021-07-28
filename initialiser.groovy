
import static com.atlassian.jira.issue.IssueFieldConstants.ISSUE_TYPE
import com.atlassian.jira.component.ComponentAccessor
import com.onresolve.jira.groovy.user.FieldBehaviours
import groovy.transform.BaseScript

@BaseScript FieldBehaviours fieldBehaviours

def allIssueTypes = ComponentAccessor.constantsManager.allIssueTypeObjects
def issueTypeField = getFieldById(ISSUE_TYPE)

def availableIssueTypes = []

availableIssueTypes.addAll(allIssueTypes.findAll {
    it.name in ["Plan", "Factory Audit Findings"] }
)
issueTypeField.setFieldOptions(availableIssueTypes)
