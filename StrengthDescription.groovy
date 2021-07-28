
import com.atlassian.jira.component.ComponentAccessor
import com.atlassian.jira.issue.customfields.option.Option
import com.atlassian.jira.issue.fields.CustomField
import com.atlassian.jira.issue.Issue
import com.onresolve.jira.groovy.user.FieldBehaviours
import groovy.transform.BaseScript
import org.apache.log4j.Level
import org.apache.log4j.Logger

@BaseScript FieldBehaviours fieldBehaviours

def log = Logger.getLogger("com.wdc.scripts")
log.setLevel(Level.INFO)

Issue issue = underlyingIssue

def cfTypeofAuditField = getFieldByName("Type of Audit")
CustomField cfTypeOfAudit = ComponentAccessor.getCustomFieldManager().getCustomFieldObject(cfTypeofAuditField?.fieldId)
Option TOA = issue.getCustomFieldValue(cfTypeOfAudit) as Option

// Fields you want to hide

def cfStandard = getFieldByName("Standard")
def cfClause = getFieldByName("Clause")
def cfSubClause = getFieldByName("Sub Clause")
def cfSubSubClause = getFieldByName("Sub Sub Clause")
def cfProcessGroup = getFieldByName("Process Group")
def cfPractice = getFieldByName("Practice")
def cfSubPractice = getFieldByName("Sub Practice")

if (TOA != null ) {
    def toaValue = TOA?.value
    // Type of Audit value is Asessment
    if (toaValue == "Assessment") {
        cfStandard.setHidden(false)
        cfClause.setHidden(true)
        cfSubClause.setHidden(true)
        cfSubSubClause.setHidden(true)

        cfStandard.setRequired(true)
        cfClause.setRequired(false)
        cfSubClause.setRequired(false)
        cfSubSubClause.setRequired(false)

        cfProcessGroup.setHidden(false)
        cfPractice.setHidden(false)
        cfSubPractice.setHidden(false)
        cfProcessGroup.setRequired(true)
        cfPractice.setRequired(true)
        //cfSubPractice.setRequired(false)
    }

// Type of Audit value
    if (toaValue in ["Customer", "Supplier", "Safety", "CTPAT"] ) {
        cfStandard.setHidden(true)
        cfClause.setHidden(true)
        cfSubClause.setHidden(true)
        cfSubSubClause.setHidden(true)
        cfStandard.setRequired(false)
        cfClause.setRequired(false)
        cfSubClause.setRequired(false)
        cfSubSubClause.setRequired(false)
        cfProcessGroup.setHidden(true)
        cfPractice.setHidden(true)
        cfSubPractice.setHidden(true)
        cfProcessGroup.setRequired(false)
        cfPractice.setRequired(false)
        cfSubPractice.setRequired(false)
    }

// Type of Audit value is Internal or External
    if (toaValue in ["Internal", "External"] ) {
        cfStandard.setHidden(false)
        cfClause.setHidden(false)
        cfSubClause.setHidden(false)
        cfSubSubClause.setHidden(false)
        cfStandard.setRequired(true)
        cfClause.setRequired(true)
        cfSubClause.setRequired(true)
        cfSubSubClause.setRequired(true)
        cfProcessGroup.setHidden(true)
        cfPractice.setHidden(true)
        cfSubPractice.setHidden(true)
        cfProcessGroup.setRequired(false)
        cfPractice.setRequired(false)
        cfSubPractice.setRequired(false)
    }
}

