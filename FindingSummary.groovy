import com.atlassian.jira.component.ComponentAccessor
import com.atlassian.jira.issue.customfields.option.Option
import com.atlassian.jira.issue.fields.CustomField
import com.atlassian.jira.issue.Issue
import com.onresolve.jira.groovy.user.FieldBehaviours
import groovy.transform.BaseScript

@BaseScript FieldBehaviours fieldBehaviours

Issue issue = underlyingIssue
def cfm = ComponentAccessor.getCustomFieldManager()
def optionManager = ComponentAccessor.optionsManager
// Type of Audit
CustomField cfTypeOfAudit = cfm.getCustomFieldObjects(issue).find { it.name == "Type of Audit" }
Option TOA = (Option)issue.getCustomFieldValue(cfTypeOfAudit)

def cfStandard = getFieldByName("Standard")
def cfClause = getFieldByName("Clause")
def cfSubClause = getFieldByName("Sub Clause")
def cfSubSubClause = getFieldByName("Sub Sub Clause")
def cfctpatRiskArea = getFieldByName("CTPAT Risk Area")
def cfFindingCategory = getFieldByName("Finding Category")
def cfNCCategory = getFieldByName("NC Category")
def cfImpDueDate = getFieldByName("Implementation Due Date")
def cfVerDueDate = getFieldByName("Verification Due Date")
def findingCategoryMapping = [
        "Customer":["Documentation", "Ergonomics & Health & safety", "ESD", "Handling & material storage",
                    "Process Control", "Test", "Traceability", "Training", "Yield", "Others", "Supplier Quality",
                    "Change Management", "Equipment Control", "Tooling Management", "QMS", "Design & Spec Management",
                    "Production Readiness", "Product Management", "Inspection Device Control", "FA Support",
                    "Internal Audit", "Process Security Management", "Product Security Management", "6S",
                    "Cleanroom Cleanliness", "Workmanship", "IMS Awareness", "Communication",
                    "Legal Compliance", "Competency"],
        "others":["Contingency plans", "Label Management", "Change Management", "Error proof", "Performance Management",
                  "Work environment", "Equipment Maintenance", "Nonconformity and corrective action",
                  "Fail lock step implementation", "Continual improvement", "Risk and Opportunity Management",
                  "Document Management", "Metrics (KPIs)", "Process / Standard Adherence", "CSR", "Missing Record",
                  "Process Interactions/linkages", "Process / Tool Enhancement", "Missing details/clarity in Procedure",
                  "Procedure not current/up to date", "Lab Controls", "Revision Control", "BPM", "Process Adherence",
                  "Documentation Missing /Needs Improvement", "Resource/Infrastructure Planning", "project Planning",
                  "Document Control", "Process Performance Evaluation", "Training",
                  "Existing Process controls not mapped", "Version Control", "Project Risk Assessment",
                  "Document Review", "Requirements  Traceability", "Design & Dev Review", "BPM Review",
                  "Outsourced Process Evaluation", "KPI", "Lessons Learnt", "Process Risk", "IMS Awareness",
                  "Communication", "Legal Compliance", "Competency"
        ]
]
// setting limited option/value for Finding Category Field
def optionMap = new LinkedHashMap<String, String>()

if (TOA != null && (TOA.value != "Customer" ) ) {
    optionMap["-1"] = "None"
    // reading a dictionary
    def allOthersValues =  findingCategoryMapping.get("others")
    // create custom field object for Finding Category based on Field ID
    def cfFindingCategoryField = cfm.getCustomFieldObject(cfFindingCategory?.fieldId)
    def cfFindingCategoryConfig = cfFindingCategoryField?.getRelevantConfig(issueContext)
    def optionsForFC = optionManager.getOptions(cfFindingCategoryConfig).findAll { it.value in allOthersValues }
    optionsForFC.collectEntries(optionMap) { option ->
        def optionValue = option.value
        [(option.optionId.toString()):optionValue]
    }
    cfFindingCategory.setFieldOptions(optionMap)
}

// Type of Audit value like Internal or External
if (TOA != null && (TOA.value == "Internal" || TOA.value == "External") ) {
    cfStandard.setHidden(false)
    cfClause.setHidden(false)
    cfSubClause.setHidden(false)
    cfSubSubClause.setHidden(false)
    cfFindingCategory.setHidden(false)
    cfImpDueDate.setHidden(true)
    cfVerDueDate.setHidden(true)
    cfStandard.setRequired(true)
    cfClause.setRequired(true)
    cfSubSubClause.setRequired(true)
    cfImpDueDate.setRequired(false)
    cfVerDueDate.setRequired(false)
    cfctpatRiskArea.setHidden(true)
}

// Type of Audit value like Internal or External
if (TOA != null && (TOA.value == "Supplier" || TOA.value == "Safety") ) {
    cfStandard.setHidden(true)
    cfClause.setHidden(true)
    cfSubClause.setHidden(true)
    cfSubSubClause.setHidden(true)
    cfFindingCategory.setHidden(false)
    cfImpDueDate.setHidden(true)
    cfVerDueDate.setHidden(true)
    cfStandard.setRequired(false)
    cfClause.setRequired(false)
    cfSubClause.setRequired(false)
    cfSubSubClause.setRequired(false)
    cfImpDueDate.setRequired(false)
    cfVerDueDate.setRequired(false)
    cfctpatRiskArea.setHidden(true)
}

// Type of Audit value is CTPAT
if (TOA != null && (TOA.value == "CTPAT" ) ) {
    cfctpatRiskArea.setHidden(false)
    cfStandard.setHidden(true)
    cfClause.setHidden(true)
    cfSubClause.setHidden(true)
    cfSubSubClause.setHidden(true)
    cfFindingCategory.setHidden(true)
    cfImpDueDate.setHidden(true)
    cfVerDueDate.setHidden(true)
    cfStandard.setRequired(false)
    cfClause.setRequired(false)
    cfSubClause.setRequired(false)
    cfSubSubClause.setRequired(false)
    cfImpDueDate.setRequired(false)
    cfVerDueDate.setRequired(false)
}

// Type of Audit value is Customer
if (TOA != null && (TOA.value == "Customer" ) ) {
    cfStandard.setHidden(true)
    cfClause.setHidden(true)
    cfSubClause.setHidden(true)
    cfSubSubClause.setHidden(true)
    cfNCCategory.setHidden(true)
    cfctpatRiskArea.setHidden(true)
    cfStandard.setRequired(false)
    cfClause.setRequired(false)
    cfSubClause.setRequired(false)
    cfSubSubClause.setRequired(false)
    cfFindingCategory.setRequired(true)
    optionMap["-1"] = "None"
    // reading a dictionary
    def allCustomerValues =  findingCategoryMapping.get(TOA.value)
    // create custom field object for Finding Category based on Field ID
    def cfFindingCategoryField = cfm.getCustomFieldObject(cfFindingCategory?.fieldId)
    def cfFindingCategoryConfig = cfFindingCategoryField?.getRelevantConfig(issueContext)
    def optionsForFC = optionManager.getOptions(cfFindingCategoryConfig).findAll { it.value in allCustomerValues }
    optionsForFC.collectEntries(optionMap) { option ->
        def optionValue = option.value
        [(option.optionId.toString()):optionValue]
    }
    cfFindingCategory.setFieldOptions(optionMap)
    cfImpDueDate.setRequired(true)
    cfVerDueDate.setRequired(true)
}
