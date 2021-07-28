package com.wdc.kms.behaviours.AMS

import com.atlassian.jira.component.ComponentAccessor
import com.atlassian.jira.issue.Issue
import com.atlassian.jira.issue.customfields.option.Option
import com.onresolve.jira.groovy.user.FieldBehaviours
import groovy.transform.BaseScript
import org.apache.log4j.Level
import org.apache.log4j.Logger

@BaseScript FieldBehaviours fieldBehaviours

def log = Logger.getLogger("com.wdc.scripts")
log.setLevel(Level.DEBUG)

def cfStandard = getFieldByName("Standard")
def cfClause = getFieldByName("Clause")
def cfProcessGroup = getFieldByName("Process Group")
def cfSubSubClause = getFieldByName("Sub Sub Clause")
def cfRBASubSubClause = getFieldByName("RBA Sub Sub Clause")
def cfSubClause = getFieldByName("Sub Clause")
def cfEsdAuditArea = getFieldByName("ESD Audit Area")
def cfEsdAuditItem = getFieldByName("ESD Audit Item")
def cfEsdAuditGroup = getFieldByName("ESD Process Group")
def cfBuildingLevel = getFieldByName("Building Level")
def cfBuilding = getFieldByName("Building")

def standardValue = cfStandard?.getValue() as String

if (standardValue && standardValue == "RBA") {
    cfRBASubSubClause.setHidden(false)
    cfRBASubSubClause.setRequired(true)
    cfSubSubClause.setHidden(true)
    cfSubSubClause.setRequired(false)
} else {
    cfRBASubSubClause.setHidden(true)
    cfRBASubSubClause.setRequired(false)
    cfSubSubClause.setHidden(false)
    cfSubSubClause.setRequired(true)
}

if (standardValue && standardValue == "Common(IMS)") {
    cfSubClause.setHidden(false)
    cfSubClause.setRequired(false)
} else {
    cfSubClause.setHidden(false)
}

if (standardValue && standardValue == "ESD") {
    cfEsdAuditArea.setHidden(false)
    cfEsdAuditItem.setHidden(false)
    cfEsdAuditGroup.setHidden(false)
    cfBuildingLevel.setHidden(false)
    cfBuilding.setHidden(false)
} else {
    cfEsdAuditArea.setHidden(true)
    cfEsdAuditItem.setHidden(true)
    cfEsdAuditGroup.setHidden(true)
    cfBuildingLevel.setHidden(true)
    cfBuilding.setHidden(true)
}

def standardMapping = [
        "OHSAS_18001":["4.1 General Requirements",
                       "4.2 OH&S policy",
                       "4.3 Planning",
                       "4.4 Implementation and operation",
                       "4.5 Checking",
                       "4.6 Management Review"],
        "ISO_Others":["CL4_Context_of_the_organization",
                      "CL4_Context_of_the_organization",
                      "CL5_Leadership",
                      "CL6_Planning",
                      "CL7_Support",
                      "CL8_Operation",
                      "CL9_Performance_Evaluation",
                      "CL10_Improvement"],
        "ESD":["5.0 PERSONNEL SAFETY",
               "6.0 ESD CONTROL PROGRAM",
               "7.0 ESD CONTROL PROGRAM ADMINISTRATIVE REQUIREMENTS",
               "8.0 ESD CONTROL PROGRAM PLAN TECHNICAL REQUIREMENTS"],
        "RBA":["Labor",
               "Health & Safety",
               "Environment",
               "Ethics",
               "Management System"],
        "VDA":["P2 Project Management",
               "P3 Planning the product and process development",
               "P4 Implementation of the product and process development process",
               "P5 Supplier management",
               "P6 Process analysis/Production",
               "P7 Customer Care/Customer Satisfaction/Service"],

        "ASPICE":["Acquisition process group (ACQ)",
                  "Supply process group (SPL)",
                  "System Engineering process group (SYS)",
                  "Software Engineering process group (SWE)",
                  "Supporting life cycle processes (SUP)",
                  "Management process group (MAN)",
                  "Process Improvement process group (PIM)",
                  "Reuse process group (REU)"],

        "IMS":["test1",
               "test2",
               "test3",
               "est4"]

]
def optionManager = ComponentAccessor.getOptionsManager()
def optionMap = new LinkedHashMap<String, String>()
optionMap["-1"] = "None"

def optionMap2 = new LinkedHashMap<String, String>()
optionMap2["-1"] = "None"

def cfm = ComponentAccessor.customFieldManager

Issue currentIssue = underlyingIssue
def cfTypeOfAudit = cfm.getCustomFieldObjects(currentIssue).find { it.name == "Type of Audit" }

if (cfTypeOfAudit && cfStandard) {
    def typeOfAuditOption = currentIssue.getCustomFieldValue(cfTypeOfAudit) as Option
    if (typeOfAuditOption && typeOfAuditOption.value == "Assessment") {
        def standardOptions = new LinkedHashMap<String, String>()
        standardOptions["-1"] = "None"
        def cfStandardField = cfm.getCustomFieldObject(cfStandard?.getFieldId())
        def standardFieldConfig = cfStandardField?.getRelevantConfig(currentIssue)
        def allStandardOptions = optionManager.getOptions(standardFieldConfig)
        def optionsForStandard = allStandardOptions.findAll { it.value in ["VDA 6.3", "ASPICE"] }
        optionsForStandard.collectEntries(standardOptions) { option ->
            def optionValue = option.value
            [(option.optionId.toString()):optionValue]
        }
        cfStandard.setFieldOptions(standardOptions)
    }
}

def clauseConfig = cfm.getCustomFieldObject(cfClause?.fieldId)?.getRelevantConfig(issueContext)
def ProcessGroupConfig = cfm.getCustomFieldObject(cfProcessGroup?.fieldId)?.getRelevantConfig(issueContext)
def optionsForClause
def optionsForProcessGroup

if (standardValue == "OHSAS 18001") {
    def iso_1800 = standardMapping.get("OHSAS_18001")
    optionsForClause = optionManager.getOptions(clauseConfig).findAll { it.value in iso_1800 }
} else if (standardValue == "RBA") {
    def rba = standardMapping.get("RBA")
    optionsForClause = optionManager.getOptions(clauseConfig).findAll { it.value in rba }
} else if (standardValue == "ESD") {
    def esd = standardMapping.get("ESD")
    optionsForClause = optionManager.getOptions(clauseConfig).findAll { it.value in esd }
} else if (standardValue == "VDA 6.3") {
    def vda = standardMapping.get("VDA")
    optionsForProcessGroup = optionManager.getOptions(ProcessGroupConfig).findAll { it.value in vda }
    log.info("optionsForProcessGroup ${optionsForProcessGroup}")
} else if (standardValue == "ASPICE") {
    def aspice = standardMapping.get("ASPICE")
    optionsForProcessGroup = optionManager.getOptions(ProcessGroupConfig).findAll { it.value in aspice }
} else if (standardValue == "IMS") {
    def ims = standardMapping.get("IMS")
    optionsForProcessGroup = optionManager.getOptions(ProcessGroupConfig).findAll { it.value in ims }
} else {
    def other_values =  standardMapping.get("ISO_Others")
    optionsForClause = optionManager.getOptions(clauseConfig).findAll { it.value in other_values }
}

if (optionsForClause) {
    optionsForClause.collectEntries(optionMap) { option ->
        def optionValue = option.value
        [(option.optionId.toString()):optionValue]
    }
    cfClause.setFieldOptions(optionMap)
}
if (optionsForProcessGroup) {
    optionsForProcessGroup.collectEntries(optionMap2) { option ->
        def optionValue2 = option.value
        log.info("optionValue2 ${optionValue2}")
        [(option.optionId.toString()):optionValue2]
    }
    log.info("optionMap2 ${optionMap2}")
    cfProcessGroup.setFieldOptions(optionMap2)
}
