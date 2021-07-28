
import com.atlassian.jira.component.ComponentAccessor
import com.onresolve.jira.groovy.user.FieldBehaviours
import groovy.transform.BaseScript
import org.apache.log4j.Level
import org.apache.log4j.Logger

@BaseScript FieldBehaviours fieldBehaviours

def log = Logger.getLogger("com.wdc.scripts")
log.setLevel(Level.INFO)

def businessLineMappings = [
        "HDD":["HDD-BPI", "HDD-KL", "HDD-PRB", "HDD-Brazil", "HDD-FUJ"],
        "Media":["MO-PN", "MO-JO", "MO-SW", "MO-SZ", "MO-SJ", "MO-GO", "External Media"],
        "Wafer":["Wafer-Fremont", "Wafer-GO"],
        "Head":["Great Oaks", "Fremont", "PHO", "THO", "HSPC", "External Head"],
        "FPG":["SDIL", "SDIN", "SDKK", "SDSH", "SDSJ", "SDSM", "SDSS", "SDUS", "SDTW(H)", "SDTW(T)", "SDXX",
               "SDSM-SIP", "SDSM-SSD"],
        "DCS":["SDUS"],
        "Others":["CM", "Supplier"],
        "IMS-GPO":["SDUS"],
        "Content Solutions":["Content Solutions"]
]

def businessLineToBusinessUnit = [
        "HDD":["Automotive", "C-enterprise", "P-enterprise", "Desktop", "Mobile", "Support", "Manufacturing", "Others"],
        "Media":["Engineering", "Manufacturing", "Support", "Others"],
        "Wafer":["Manufacturing", "Equipment and Mfg. System", "Engineering and Integration", "Support", "Others"],
        "Head":["FAB1-SLD Process", "HMY-SLD Process", "FAB2 SLD Process", "HGA Process", "Support", "Operations",
                "Others", "SDET"],
        "FPG":["RPG", "iNAND", "eSSD", "cSSD", "ASIC", "Memory_Technology", "Manufacturing", "Support", "Others"],
        "DCS":["DCS", "Manufacturing", "Support", "Others"],
        "IMS-GPO":["IMS-GPO"],
        "Content Solutions":["Content Solutions"]
]

def cfBusinessLine = getFieldByName("Business Line")
def cfBusinessLineValue = cfBusinessLine.getValue() as String
def cfBusinessUnit = getFieldByName("Business Unit")
def cfResponsibleLocation = getFieldByName("Responsible location")

def optionMap = new LinkedHashMap<String, String>()
optionMap["-1"] = "None"

def buMap = new LinkedHashMap<String, String>()
buMap["-1"] = "None"

if (cfBusinessLineValue ) {
    def rfOptionValues =  businessLineMappings.get(cfBusinessLineValue)
    def customFieldManager = ComponentAccessor.customFieldManager
    def cfResponsibleLocationField = customFieldManager.getCustomFieldObject(cfResponsibleLocation?.fieldId)
    def moduleCFConfig = cfResponsibleLocationField?.getRelevantConfig(issueContext)
    def optionManager = ComponentAccessor.optionsManager
    def optionsForRL = optionManager.getOptions(moduleCFConfig).findAll { it.value in rfOptionValues }

    optionsForRL.collectEntries(optionMap) { option ->
        def optionValue = option.value
        [(option.optionId.toString()):optionValue]
    }

    //Business Line --> Business Unit
    def buOptionValues =  businessLineToBusinessUnit.get(cfBusinessLineValue)
    def buCFConfig = customFieldManager.getCustomFieldObject(cfBusinessUnit?.fieldId)?.getRelevantConfig(issueContext)
    def optionsForBU
    if (cfBusinessLineValue == "Others") {
        optionsForBU = optionManager.getOptions(buCFConfig).findAll()
    } else {
        optionsForBU = optionManager.getOptions(buCFConfig).findAll { it.value in buOptionValues }
    }

    optionsForBU.collectEntries(buMap) { option ->
        def optionValue = option.value
        [(option.optionId.toString()):optionValue]
    }
}

cfResponsibleLocation.setRequired(Boolean.TRUE)
cfResponsibleLocation.setFieldOptions(optionMap)
cfBusinessUnit.setFieldOptions(buMap)
