
import com.atlassian.jira.component.ComponentAccessor
import com.onresolve.jira.groovy.user.FieldBehaviours
import groovy.transform.BaseScript

@BaseScript FieldBehaviours fieldBehaviours

def iatf = [
        "CL4_Context_of_the_organization":["4.1 Understanding the organization and its context",
                                             "4.2 Understanding the needs and expectations of interested parties",
                                             "4.3 Determining the scope of the quality management system",
                                             "4.4 Quality management system and its processes"  ],

        "CL5_Leadership":["5.1 Leadership and commitment", "5.2 Policy",
                "5.3 Organizational roles, responsibilities and authorities"],
        "CL6_Planning":["6.1 Actions to address risks and opportunities",
                        "6.2 Quality objectives and planning to achieve them",
                        "6.3 Planning of changes" ],
        "CL7_Support":["7.1 Resources", "7.2 Competence",  "7.3 Awareness", "7.4 Communication",
                       "7.5 Documented information"],
        "CL8_Operation":["8.1 Operational planning and control",
                         "8.2 Requirements for products and services",
                         "8.3 Design and development of products and services",
                         "8.4 Control of externally provided processes, products and services",
                         "8.5 Production and service provision",
                         "8.6 Release of products and services",
                         "8.7 Control of nonconforming outputs"],
        "CL9_Performance_Evaluation":["9.1 Monitoring, measurement, analysis and evaluation", "9.2 Internal audit",
                                      "9.3 Management review" ],
        "CL10_Improvement":["10.1 General", "10.2 Nonconformity and corrective action", "10.3 Continual improvement"]

]

def iso_45001 = [
        "CL4_Context_of_the_organization":["4.1 Understanding the organization and its context",
                                             "4.2 Understanding the needs and expectations of interested parties",
                                             "4.3 Determining the scope of the quality management system",
                                             "4.4 OH&S management system"  ],

        "CL5_Leadership":["5.1 Leadership and commitment",
                          "5.2 OH&S policy",
                          "5.3 Organizational roles, responsibilities and authorities",
                          "5.4 Consultation and participation of workers"],

        "CL6_Planning":["6.1 Actions to address risks and opportunities",
                        "6.2 OH&S objectives and planning to achieve them"],

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
                            "10.2 Incident, nonconformity and corrective action",
                            "10.3 Continual improvement"]

]

def iso_14001 = [
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

def iso_9001 = [
        "CL4_Context_of_the_organization":["4.1 Understanding the organization and its context",
                                             "4.2 Understanding the needs and expectations of interested parties",
                                             "4.3 Determining the scope of the quality management system",
                                             "4.4 Quality management system and its processes"  ],

        "CL5_Leadership":["5.1 Leadership and commitment",
                          "5.2 Policy",
                          "5.3 Organizational roles, responsibilities and authorities"],

        "CL6_Planning":["6.1 Actions to address risks and opportunities",
                        "6.2 Quality objectives and planning to achieve them",
                        "6.3 Planning of changes"],

        "CL7_Support":["7.1 Resources",
                       "7.2 Competence",
                       "7.3 Awareness",
                       "7.4 Communication",
                       "7.5 Documented information"],

        "CL8_Operation":["8.1 Operational planning and control",
                         "8.2 Requirements for products and services",
                         "8.3 Design and development of products and services",
                         "8.4 Control of externally provided processes, products and services",
                         "8.5 Production and service provision",
                         "8.6 Release of products and services",
                         "8.7 Control of nonconforming outputs"],

        "CL9_Performance_Evaluation":["9.1 Monitoring, measurement, analysis and evaluation",
                                      "9.2 Internal audit",
                                      "9.3 Management review" ],

        "CL10_Improvement":["10.1 General",
                            "10.2 Nonconformity and corrective action",
                            "10.3 Continual improvement"]

]

def bcms = [
        "CL4_Context_of_the_organization":["4.1 Understanding the organization and its context",
                                             "4.2 Understanding the needs and expectations of interested parties",
                                             "4.3 Determining the scope of the business continuity management system",
                                             "4.4 Business continuity management system"  ],

        "CL5_Leadership":["5.1 Leadership and commitment",
                          "5.2 Management commitment",
                          "5.3 Policy",
                          "5.4 Organizational roles, responsibilities and authorities"],

        "CL6_Planning":["6.1 Actions to address risks and opportunities",
                        "6.2 Business continuity objectives and plans to achieve them" ],

        "CL7_Support":["7.1 Resources",
                       "7.2 Competence",
                       "7.3 Awareness",
                       "7.4 Communication",
                       "7.5 Documented information"],

        "CL8_Operation":["8.1 Operational planning and control",
                         "8.2 Business impact analysis and risk assessment",
                         "8.3 Business continuity strategy",
                         "8.4 Establish and implement business continuity procedures",
                         "8.5 Exercising and testing",
                         "8.6 Evaluation of business continuity documentation and capabilities"],

        "CL9_Performance_Evaluation":["9.1 Monitoring, measurement, analysis and evaluation",
                                      "9.2 Internal audit",
                                      "9.3 Management review"],

        "CL10_Improvement":["10.1 Nonconformity and corrective action",
                            "10.2 Continual improvement"]

]

def ohsas_1800 = [
        "4.1 General Requirements":["4.1 General requirements"],
        "4.2 OH&S policy":["4.2 OH&S Policy"],
        "4.3 Planning":["4.3.1 Hazard identification, risk assessment and determining controls",
                        "4.3.2 Legal and other requirements",
                        "4.3.3 Objectives and programme(s)"],
        "4.4 Implementation and operation":["4.4.1 Resources, roles, responsibility, accountability and authority",
                                            "4.4.2 Competence, training and awareness",
                                            "4.4.3 Communication, participation and consultation",
                                            "4.4.3.1 Communication",
                                            "4.4.3.2 Participation and consultation",
                                            "4.4.4 Documentation",
                                            "4.4.5 Control of Documents",
                                            "4.4.6 Operational control",
                                            "4.4.7 Emergency preparedness and response"],
        "4.5 Checking":["4.5.1 Performance measurement and monitoring",
                        "4.5.2 Evaluation of compliance",
                        "4.5.3 Incident investigation, nonconformity, corrective action and preventive action",
                        "4.5.3.1 Incident investigation",
                        "4.5.3.2 Nonconformity, corrective action and preventive action",
                        "4.5.4 Control of records",
                        "4.5.5 Internal Audit"],
        "4.6 Management Review":["4.6 Management Review"]
]

def RBA = [
        "Labor":["A1) Freely Chosen Employment",
                 "A2) Young Workers",
                 "A3) Working Hours",
                 "A4) Wages and Benefits",
                 "A5) Humane Treatment",
                 "A6) Non-Discrimination",
                 "A7) Freedom of Association"],
        "Health & Safety":["B1) Occupational Safety",
                           "B2) Emergency Preparedness",
                           "B3) Occupational Injury and Illness",
                           "B4) Industrial Hygiene",
                           "B5) Physically Demanding Work",
                           "B6) Machine Safeguarding",
                           "B7) Food, Sanitation and Housing"],
        "Environment":["C1) Environmental Permits and Reporting",
                       "C2) Pollution Prevention and Resource Reduction",
                       "C3) Hazardous Substances",
                       "C4) Solid Waste",
                       "C5) Air Emissions",
                       "C6) Materials Restrictions",
                       "C7) Water Management",
                       "C8) Energy Consumption and Greenhouse Gas Emissions"],
        "Ethics":["D1) Business Integrity",
                  "D2) No Improper Advantage",
                  "D3) Disclosure of Information",
                  "D4) Intellectual Property",
                  "D5) Fair Business, Advertising and Competition",
                  "D6) Protection of Identity and Non-Retaliation",
                  "D7) Responsible Sourcing of Minerals",
                  "D8) Privacy"],
        "Management System":["E1) Company Commitment",
                             "E2) Management Accountability and Responsibility",
                             "E3) Legal and Customer Requirements",
                             "E4) Risk Assessment and Risk Management",
                             "E5) Improvement Objectives",
                             "E6) Training",
                             "E7) Communication",
                             "E8) Worker Feedback, Participation and Grievance",
                             "E9) Audits and Assessments",
                             "E10) Corrective Action Process",
                             "E11) Documentation and Records",
                             "E12) Supplier Responsibility"]
]

def ESD = [
        "5.0 PERSONNEL SAFETY":["5.0 PERSONNEL SAFETY"],
        "6.0 ESD CONTROL PROGRAM":["6.1 ESD CONTROL PROGRAM REQUIREMENTS",
                                   "6.2 ESD CONTROL PROGRAM MANAGER OR COORDINATOR",
                                   "6.3 TAILORING"],
        "7.0 ESD CONTROL PROGRAM ADMINISTRATIVE REQUIREMENTS":["7.1 ESD CONTROL PROGRAM PLAN",
                                                               "7.2 TRAINING PLAN",
                                                               "7.3 PRODUCT QUALIFICATION PLAN",
                                                               "7.4 COMPLIANCE VERIFICATION PLAN"],
        "8.0 ESD CONTROL PROGRAM PLAN TECHNICAL REQUIREMENTS":["8.1 GROUNDING / EQUIPOTENTIAL BONDING SYSTEMS",
                                                               "8.2 PERSONNEL GROUNDING",
                                                               "8.3 ESD PROTECTED AREAS (EPAS)",
                                                               "8.4 PACKAGING",
                                                               "8.5 MARKING"]
]
def IAF = [
        "IAF MD1 checkpoints":["IAF MD1 checkpoints"]
]

def cfStandard = getFieldByName("Standard")
def cfClause = getFieldByName("Clause")
def cfSubClause = getFieldByName("Sub Clause")
def standardValue = cfStandard.getValue() as String
def clause = cfClause.getValue() as String

def subClauseValues
if (standardValue == "ISO 9001") {
    subClauseValues = iso_9001.get(clause)
    cfSubClause.setRequired(Boolean.TRUE)
} else if (standardValue == "ISO 14001") {
    cfSubClause.setRequired(Boolean.TRUE)
    subClauseValues = iso_14001.get(clause)
} else if (standardValue == "IATF 16949") {
    subClauseValues = iatf.get(clause)
    cfSubClause.setRequired(Boolean.TRUE)
} else if (standardValue == "ISO 45001") {
    subClauseValues = iso_45001.get(clause)
    cfSubClause.setRequired(Boolean.TRUE)
} else if (standardValue == "OHSAS 18001") {
    subClauseValues = ohsas_1800.get(clause)
    cfSubClause.setRequired(Boolean.TRUE)
} else if (standardValue == "ISO 22301") {
    subClauseValues = bcms.get(clause)
    cfSubClause.setRequired(Boolean.TRUE)
} else if (standardValue == "Common(IMS)") {
    subClauseValues = iso_9001.get(clause)
    cfSubClause.setRequired(Boolean.FALSE)
} else if (standardValue == "RBA") {
    subClauseValues = RBA.get(clause)
    cfSubClause.setRequired(Boolean.TRUE)
} else if (standardValue == "ESD") {
    subClauseValues = ESD.get(clause)
    cfSubClause.setRequired(Boolean.TRUE)
} else if (standardValue == "IAF MD1") {
    subClauseValues = IAF.get(clause)
    cfSubClause.setRequired(Boolean.TRUE)
}

def customFieldManager = ComponentAccessor.customFieldManager
def subClauseField = customFieldManager.getCustomFieldObject(cfSubClause?.fieldId)
def cfSubClauseFieldConfig = subClauseField?.getRelevantConfig(issueContext)
def optionManager = ComponentAccessor.getOptionsManager()
def optionsForSubClause = optionManager.getOptions(cfSubClauseFieldConfig).findAll { it.value in subClauseValues }
def optionMap = new LinkedHashMap<String, String>()
optionMap["-1"] = "None"

optionsForSubClause.collectEntries(optionMap) { option ->
    def optionValue = option.value
    [(option.optionId.toString()):optionValue]
}
//set options to form field
cfSubClause.setFieldOptions(optionMap)
