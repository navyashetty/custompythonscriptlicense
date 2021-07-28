
import com.onresolve.jira.groovy.user.FieldBehaviours
import groovy.transform.BaseScript

@BaseScript FieldBehaviours fieldBehaviours

def cfAuditProgram = getFieldByName("Audit Program")
def cfAuditProgramValue = cfAuditProgram.getValue() as String
def cfBusinessLine = getFieldByName("Business Line")
def cfBusinessLineValue = cfBusinessLine.getValue() as String
def cfApplicableStandards = getFieldByName("Applicable Standards")
def cfESDprocessGroup = getFieldByName("ESD Process Group")
def cfESDauditItem = getFieldByName("ESD Audit Item")
def cfESDauditArea = getFieldByName("ESD Audit Area")
def cfBuilding = getFieldByName("Building")
def cfBuildingLevel = getFieldByName("Building Level")

if (cfAuditProgramValue == "IMS") {
    cfApplicableStandards.setHidden(false)
    cfApplicableStandards.setRequired(true )
} else if (cfAuditProgramValue == "ESD" && (cfBusinessLineValue == "Head" || cfBusinessLineValue == "HDD")) {
    cfESDprocessGroup.setHidden(false)
    cfESDauditItem.setHidden(false)
    cfESDauditArea.setHidden(false)
    cfBuilding.setHidden(false)
    cfBuildingLevel.setHidden(false)
} else {
    cfApplicableStandards.setHidden(true )
    cfESDprocessGroup.setHidden(true)
    cfESDauditItem.setHidden(true)
    cfESDauditArea.setHidden(true)
    cfBuilding.setHidden(true)
    cfBuildingLevel.setHidden(true)
    cfApplicableStandards.setRequired(false )
}
