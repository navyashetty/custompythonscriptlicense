import com.atlassian.jira.component.ComponentAccessor
import com.onresolve.jira.groovy.user.FieldBehaviours
import groovy.transform.BaseScript

@BaseScript FieldBehaviours fieldBehaviours

def RBASubSubClause = [
        "A1) Freely Chosen Employment":[
                "VAP-A1.1", "VAP-A1.2", "VAP-A1.2.1", "VAP-A1.2.2", "VAP-A1.2.3", "VAP-A1.2.4", "VAP-A1.2.5",
                "VAP-A1.2.6", "VAP-A1.2.7", "VAP-A1.3", "VAP-A1.3.1", "VAP-A1.3.2", "VAP-A1.3.3", "VAP-A1.3.4",
                "VAP-A1.3.5", "VAP-A1.4", "VAP-A1.4.1", "VAP-A1.4.2", "VAP-A1.5"],
        "A2) Young Workers":["VAP-A2.1", "VAP-A2.2", "VAP-A2.2.1", "VAP-A2.3a", "VAP-A2.3b", "VAP-A2.4a", "VAP-A2.4b"],
        "A3) Working Hours":["VAP-A3.1", "VAP-A3.2", "VAP-A3.3", "VAP-A3.4"],
        "A4) Wages and Benefits":["VAP-A4.1", "VAP-A4.2", "VAP-A4.2.1", "VAP-A4.3", "VAP-A4.4a", "VAP-A4.4b"],
        "A5) Humane Treatment":["VAP-A5.1", "VAP-A5.2", "VAP-A5.3"],
        "A6) Non-Discrimination":["VAP-A6.1", "VAP-A6.2", "VAP-A6.3"],
        "A7) Freedom of Association":["VAP-A7.1", "VAP-A7.2a", "VAP-A7.2b", "VAP-A7.3", "VAP-A7.4a", "VAP-A7.4b"],
        "B1) Occupational Safety":[
                "VAP-B1.1a", "VAP-B1.1b", "VAP-B1.2", "VAP-B1.3a", "VAP-B1.3b", "VAP-B1.4a", "VAP-B1.4b"],
        "B2) Emergency Preparedness":[
                "VAP-B2.1a", "VAP-B2.1b", "VAP-B2.2", "VAP-B2.3", "VAP-B2.4", "VAP-B2.5", "VAP-B2.6a", "VAP-B2.6b"],
        "B3) Occupational Injury and Illness":[
                "VAP-B3.1a", "VAP-B3.1b", "VAP-B3.2a", "VAP-B3.2b", "VAP-B3.3", "VAP-B3.3.1", "VAP-B3.4"],
        "B4) Industrial Hygiene":["VAP-B4.1a", "VAP-B4.1b", "VAP-B4.2"],
        "B5) Physically Demanding Work":["VAP-B5.1a", "VAP-B5.1b"],
        "B6) Machine Safeguarding":["VAP-B6.1a", "VAP-B6.1b", "VAP-B6.2a", "VAP-B6.2b"],
        "B7) Food, Sanitation and Housing":["VAP-B7.1a", "VAP-B7.1b", "VAP-B7.2.1a", "VAP-B7.2.1b", "VAP-B7.2a",
                                            "VAP-B7.2b", "VAP-B7.3a", "VAP-B7.3b"],
        "C1) Environmental Permits and Reporting":["VAP-C1.1a", "VAP-C1.1b", "VAP-C1.2a", "VAP-C1.2b"],
        "C2) Pollution Prevention and Resource Reduction":["VAP-C2.1"],
        "C3) Hazardous Substances":["VAP-C3.1a", "VAP-C3.1b", "VAP-C3.2"],
        "C4) Solid Waste":["VAP-C4.1a", "VAP-C4.1b"],
        "C5) Air Emissions":["VAP-C5.1a", "VAP-C5.1b", "VAP-C5.2a", "VAP-C5.2b"],
        "C6) Materials Restrictions":["VAP-C6.1a", "VAP-C6.1b"],
        "C7) Water Management":["VAP-C7.1"],
        "C8) Energy Consumption and Greenhouse Gas Emissions":["VAP-C8.1a", "VAP-C8.1b", "VAP-C8.2"],
        "D1) Business Integrity":["VAP-D1.1", "VAP-D1.1.1"],
        "D2) No Improper Advantage":["VAP-D2.1", "VAP-D2.1.1"],
        "D3) Disclosure of Information":["VAP-D3.1"],
        "D4) Intellectual Property":["VAP-D4.1"],
        "D5) Fair Business, Advertising and Competition":["VAP-D5.1"],
        "D6) Protection of Identity and Non-Retaliation":["VAP-D6.1", "VAP-D6.1.1"],
        "D7) Responsible Sourcing of Minerals":["VAP-D7.1a", "VAP-D7.1b"],
        "D8) Privacy":["VAP-D8.1"],
        "E1) Company Commitment":["VAP-E1.1"],
        "E2) Management Accountability and Responsibility":["VAP-E2.1", "VAP-E2.2"],
        "E3) Legal and Customer Requirements":["VAP-E3.1"],
        "E4) Risk Assessment and Risk Management":["VAP-E4.1"],
        "E5) Improvement Objectives":["VAP-E5.1"],
        "E6) Training":["VAP-E6.1", "VAP-E6.1.1"],
        "E7) Communication":["VAP-E7.1"],
        "E8) Worker Feedback, Participation and Grievance":["VAP-E8.1", "VAP-E8.2"],
        "E9) Audits and Assessments":["VAP-E9.1", "VAP-E9.1.1"],
        "E10) Corrective Action Process":["VAP-E10.1a", "VAP-E10.1b"],
        "E11) Documentation and Records":["VAP-E11.1"],
        "E12) Supplier Responsibility":["VAP-E12.1.1", "VAP-E12.1b", "VAP-E12.2.1", "VAP-E12.2b", "VAP-E12.3.1"]
]

def cfStandard = getFieldByName("Standard")
def cfSubClause = getFieldByName("Sub Clause")
def RBASubSubClauseField = getFieldByName("RBA Sub Sub Clause")
def standardValue = cfStandard?.value as String

def optionMap = new LinkedHashMap<String, String>()
optionMap["-1"] = "None"
if (standardValue == "RBA") {
    def cfm = ComponentAccessor.customFieldManager
    def moduleCFConfig = cfm.getCustomFieldObject(RBASubSubClauseField?.fieldId)?.getRelevantConfig(issueContext)
    def optionManager = ComponentAccessor.getOptionsManager()
    def subClauseValue = cfSubClause?.value as String
    def rbaSubClauseValues = RBASubSubClause.get(subClauseValue)
    def optionsForSubClause = optionManager.getOptions(moduleCFConfig)?.findAll { it.value in rbaSubClauseValues }
    optionsForSubClause.collectEntries(optionMap) { option ->
        def optionValue = option.value
        [(option.optionId.toString()):optionValue]
    }
}
RBASubSubClauseField.setFieldOptions(optionMap)
