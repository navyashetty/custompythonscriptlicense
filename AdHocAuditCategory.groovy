import com.onresolve.jira.groovy.user.FieldBehaviours
import groovy.transform.BaseScript

@BaseScript FieldBehaviours fieldBehaviours

def cfAdhocCategory = getFieldByName("AdHoc Audit Category")
def cfAdhocCategoryValue = cfAdhocCategory.getValue() as String

def cfBusinessLine = getFieldByName("Business Line")
def cfBusinessLineValue = cfBusinessLine.getValue() as String

def cfESDprocessGroup = getFieldByName("ESD Process Group")
def cfESDauditItem = getFieldByName("ESD Audit Item")
def cfESDauditArea = getFieldByName("ESD Audit Area")
def cfBuilding = getFieldByName("Building")
def cfBuildingLevel = getFieldByName("Building Level")

if (cfAdhocCategoryValue == "ESD" && (cfBusinessLineValue == "Head" || cfBusinessLineValue == "HDD")) {
    cfESDprocessGroup.setHidden(false)
    cfESDauditItem.setHidden(false)
    cfESDauditArea.setHidden(false)
    cfBuilding.setHidden(false)
    cfBuildingLevel.setHidden(false)
} else {
    cfESDprocessGroup.setHidden(true)
    cfESDauditItem.setHidden(true)
    cfESDauditArea.setHidden(true)
    cfBuilding.setHidden(true)
    cfBuildingLevel.setHidden(true)
}
