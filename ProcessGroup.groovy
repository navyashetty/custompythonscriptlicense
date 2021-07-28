
import com.atlassian.jira.component.ComponentAccessor
import com.onresolve.jira.groovy.user.FieldBehaviours
import groovy.transform.BaseScript

@BaseScript FieldBehaviours fieldBehaviours

def VDA = [
        "P2 Project Management":[
            "P2.1 Is a project management established with a project organisation?",
            "P2.2 Are all resources required for the project implementation planned and" +
                    " available and are changes reported?",
            "P2.3 Is there a project plan and has this been agreed with the customer?",
            "P2.4 Is the advanced product quality planning implemented within the project and" +
                    " monitored for compliance?",
            "P2.5 Are the procurement activities of the project implemented and monitored for compliance?",
            "P2.6 Is change management within the project ensured by the project organisation?",
            "P2.7 Is there an escalation process established and is this effectively implemented?"],

        "P3 Planning the product and process development":[
            "P3.1 Are the specific product requirements available?",
            "P3.2 Is the feasibility comprehensively evaluated?",
            "P3.3 Are the activities for the product development planned in detail?",
            "P3.4 Are the activities planned for customer care/customer satisfaction/customer" +
                    " service and field failure analysis?",
            "P3.5 Have the necessary resources been taken into account for the product development?"],

        "P4 Implementation of the product and process development process":[
                "P4.1 Are the actions from the product development plans implemented?",
                "P4.2 Are personnel resources available and are they qualified to ensure" +
                        " the start of serial production?",
                "P4.3 Are the material resources available and suitable to ensure the start of serial production?",
                "P4.4 Are the required approvals and releases for the product development available?",
                "P4.5 Are the manufacturing and inspection specifications derived from the product development" +
                        " and are they implemented?",
                "P4.6 Is a performance test carried out under series conditions for the series release?",
                "P4.7 Are the processes established for securing customer care/customer satisfaction/customer" +
                        " service as well as the field failure analysis?",
                "P4.8 Is there a controlled method for the process handover from development to serial production?"],
        "P5 Supplier management":[
                "P5.1 Are only approved and quality-capable suppliers selected?",
                "P5.2 Are customer requirements taken into account in the supply chain?",
                "P5.3 Have target agreements for supplier performance been agreed upon and implemented?",
                "P5.4 Are the necessary releases available for purchased products and services?",
                "P5.5 Is the agreed upon quality of the purchased products and services ensured?",
                "P5.6 Are incoming goods delivered and stored appropriately?",
                "P5.7 Are personnel qualified for their respective tasks and are responsibilities defined?"],

        "P6 Process analysis/Production":[
                "P6.1 Process Input",
                "P6.2 Process Management/ Work Content",
                "P6.3 Personnel /Process Support",
                "P6.4 Material Resources",
                "P6.5 Effectiveness of the Process / Efficiency",
                "P6.6 Process Result / Output"],

        "P7 Customer Care/Customer Satisfaction/Service":[
                "P7.1 Are all requirements related to QM-System, product and process fulfilled?",
                "P7.2 Is customer service guaranteed?",
                "P7.3 Is the supply of parts guaranteed?",
                "P7.4 If there are deviations from quality requirements or complaints, are failure analyses" +
                        " carried out and corrective actions implemented effectively?",
                "P7.5 Are personnel qualified for their respective tasks and are responsibilities defined?"]
]

def ASPICE = [
        "Acquisition process group (ACQ)":["ACQ.3 Contract Agreement",
                                             "ACQ.4 Supplier Monitoring",
                                             "ACQ.11 Technical Requirements",
                                             "ACQ.12 Legal and Administrative Requirements",
                                             "ACQ.13 Project Requirements",
                                             "ACQ.14 Request for Proposals",
                                             "ACQ.15 Supplier Qualification"],

        "Supply process group (SPL)":["SPL.1 Supplier Tendering",
                                      "SPL.2 Product Release"],

        "System Engineering process group (SYS)":["SYS.1 Requirements Elicitation",
                                                  "SYS.2 System Requirements Analysis",
                                                  "SYS.3 System Architectural Design",
                                                  "SYS.4 System Integration and Integration Test",
                                                  "SYS.5 System Qualification Test"],

        "Software Engineering process group (SWE)":["SWE.1 Software Requirement Analysis",
                                                    "SWE.2 Software Architectural Design",
                                                    "SWE.3 Software Detailed Design and Unit Construction",
                                                    "SWE.4 Software Unit Verification",
                                                    "SWE.5 Software Integration and Integration Test",
                                                    "SWE.6 Software Qualification Test"],
        "Supporting life cycle processes (SUP)":["SUP.1 Quality Assurance",
                                                 "SUP.2 Verification",
                                                 "SUP.4 Joint Review",
                                                 "SUP.7 Documentation",
                                                 "SUP.8 Configuration Management",
                                                 "SUP.9 Problem Resolution Management",
                                                 "SUP.10 Change Request Management"],
        "Management process group (MAN)":["MAN.3 Project Management",
                                          "MAN.5 Risk Management",
                                          "MAN.6 Measurement"],
        "Process Improvement process group (PIM)":["PIM.3 Process Improvement"],

        "Reuse process group (REU)":["REU.2 Reuse Program Management"]

]

def IMS = [
        "CL4_Context_of_the_organization":["4.1 Understanding the organization and its context",
                                             "4.2 Understanding the needs and expectations of interested parties",
                                             "4.3 Determining the scope of the environmental management system",
                                             "4.4 Environmental management system"  ],

        "CL5_Leadership":["5.1 Leadership and commitment",
                          "5.2 Environmental policy",
                          "5.3 Organizational roles, responsibilities and authorities"],

        "CL6_Planning":["6.1 Actions to address risks and opportunities",
                        "6.2 Environmental objectives and planning to achieve them"],

        "CL7_Support":["7.1 Resources",
                       "7.2 Competence",
                       "7.3 Awareness",
                       "7.4 Communication",
                       "7.5 Documented information"],

        "CL8_Operation":["8.1 Operational planning and control",
                         "8.2 Emergency preparedness and response"],

        "CL9_Performance_Evaluation":["9.1 Monitoring, measurement, analysis and evaluation",
                                      "9.2 Internal audit",
                                      "9.3 Management review" ],

        "CL10_Improvement":["10.1 General",
                            "10.2 Nonconformity and corrective action",
                            "10.3 Continual improvement"]
]

def cfProcessGroup = getFieldByName("Process Group")
def cfPractice = getFieldByName("Practice")
def cfStandard = getFieldByName("Standard")

def standardValue = cfStandard.getValue() as String
def processGroup = cfProcessGroup.getValue() as String
def practiceValues

if (standardValue == "VDA 6.3") {
    practiceValues = VDA.get(processGroup)
    cfPractice.setRequired(Boolean.TRUE);
} else if (standardValue == "ASPICE") {
    cfPractice.setRequired(Boolean.TRUE);
    practiceValues = ASPICE.get(processGroup)
} else if (standardValue == "IMS") {
    practiceValues = IMS.get(processGroup)
    cfPractice.setRequired(Boolean.TRUE);
}
def customFieldManager = ComponentAccessor.customFieldManager
def practiseFieldConfig = customFieldManager.getCustomFieldObject(cfPractice?.fieldId)?.getRelevantConfig(issueContext)
def optionManager = ComponentAccessor.getOptionsManager()
def optionsForPractice = optionManager.getOptions(practiseFieldConfig).findAll { it.value in practiceValues }
def optionMap = new LinkedHashMap<String, String>()
optionMap["-1"] = "None"

optionsForPractice.collectEntries(optionMap) { option ->
    def optionValue = option.value
    [(option.optionId.toString()):optionValue]
}
cfPractice.setFieldOptions(optionMap)
