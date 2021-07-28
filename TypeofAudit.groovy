
import com.atlassian.jira.component.ComponentAccessor
import com.onresolve.jira.groovy.user.FieldBehaviours
import groovy.transform.BaseScript

@BaseScript FieldBehaviours fieldBehaviours

def cfTypeofAudit = getFieldByName("Type of Audit")
def cfTypeofAuditValue = cfTypeofAudit.getValue() as String
def isExternalSelected = (cfTypeofAuditValue == "External")
def isInternalSelected = (cfTypeofAuditValue == "Internal")
def isCustomerSelected = (cfTypeofAuditValue == "Customer")
def isSupplierSelected = (cfTypeofAuditValue == "Supplier")
def isSafetySelected = (cfTypeofAuditValue == "Safety")
def isOtherSelected = (cfTypeofAuditValue == "Other")
def isCTPATSelected = (cfTypeofAuditValue == "CTPAT")
def cfAuditProgram = getFieldByName("Audit Program")
def cfAuditClass = getFieldByName("Audit Class")
def cfAuditPriority = getFieldByName("Audit Priority")
def cfFixVersion = getFieldByName("Fix Version/s")
def cfExternalAuditor = getFieldByName("External Auditor")
def cfAuditor = getFieldByName("Auditor")
def cfAuditCoordinator = getFieldByName("Audit Coordinator")
def cfCustomer = getFieldByName("Customer Name / Supplier Name")
def cfCustomerdetails = getFieldByName("Customer Details")

cfAuditProgram.setHidden(isCustomerSelected || isSupplierSelected || isSafetySelected || isOtherSelected
        || isCTPATSelected )
cfAuditClass.setHidden(isCustomerSelected || isSupplierSelected || isSafetySelected || isOtherSelected
        || isCTPATSelected )
cfAuditPriority.setHidden(isCustomerSelected || isSupplierSelected || isSafetySelected || isOtherSelected
        || isCTPATSelected )

cfFixVersion.setHidden(isInternalSelected || isExternalSelected )
cfAuditor.setHidden(isCustomerSelected || isExternalSelected )
cfAuditCoordinator.setRequired(isCustomerSelected || isExternalSelected )
cfAuditClass.setRequired(!(isCustomerSelected || isSupplierSelected || isSafetySelected || isOtherSelected
        || isCTPATSelected ))
cfAuditProgram.setRequired(!(isCustomerSelected || isSupplierSelected || isSafetySelected || isOtherSelected
        || isCTPATSelected ))
cfExternalAuditor.setRequired(isExternalSelected || isCustomerSelected)
cfAuditPriority.setRequired(!(isCustomerSelected || isSupplierSelected || isSafetySelected || isOtherSelected
        || isCTPATSelected ))
cfFixVersion.setRequired(!(isCustomerSelected || isSupplierSelected || isSafetySelected || isOtherSelected
        || isCTPATSelected ))
cfCustomer.setHidden(!(isCustomerSelected || isSupplierSelected))
cfCustomer.setRequired(isCustomerSelected || isSupplierSelected)
cfCustomerdetails.setHidden(isInternalSelected || isExternalSelected || isSupplierSelected || isSafetySelected
        || isOtherSelected || isCTPATSelected)
cfCustomerdetails.setRequired(isCustomerSelected)
cfCustomerdetails.setHidden(!(isCustomerSelected))

cfExternalAuditor.setHidden(!(isExternalSelected || isCustomerSelected))
cfAuditCoordinator.setHidden(!(isExternalSelected || isCustomerSelected))
cfAuditor.setRequired(!(isCustomerSelected || isExternalSelected ))

def cfCommodityGroup = getFieldByName("Commodity Group")
def cfCommodityName = getFieldByName("Commodity Name")
def cfManufacturerPart = getFieldByName("Manufacturer part#")
def cfProgramName = getFieldByName("Program Name")
def cfProjectNameId = getFieldByName("Project Name-ID")
def cfCMSupplierName = getFieldByName("CM / Supplier Name")
def cfInputCMSupplierName = getFieldByName("Input CM / Supplier Name")

cfCMSupplierName.setHidden(!(isSupplierSelected))
cfCMSupplierName.setRequired(isSupplierSelected)

if (isSupplierSelected) {
    cfCommodityGroup.setHidden(false)
    cfCommodityGroup.setRequired(true)

    cfCommodityName.setHidden(false)
    cfCommodityName.setRequired(true)

    cfManufacturerPart.setHidden(false)
    cfManufacturerPart.setRequired(true)

    cfProgramName.setHidden(false)
    cfProgramName.setRequired(true)

    cfProjectNameId.setHidden(false)
    cfProjectNameId.setRequired(true)

    cfInputCMSupplierName.setHidden(false)
    cfInputCMSupplierName.setRequired(true)
} else {
    cfCommodityGroup.setHidden(true)
    cfCommodityGroup.setRequired(false)

    cfCommodityName.setHidden(true)
    cfCommodityName.setRequired(false)

    cfManufacturerPart.setHidden(true)
    cfManufacturerPart.setRequired(false)

    cfProgramName.setHidden(true)
    cfProgramName.setRequired(false)

    cfProjectNameId.setHidden(true)
    cfProjectNameId.setRequired(false)

    cfInputCMSupplierName.setHidden(true)
    cfInputCMSupplierName.setRequired(false)
}

def optionMap = new LinkedHashMap<String, String>()
optionMap["-1"] = "None"
def cfAuditProgramField = ComponentAccessor.customFieldManager.getCustomFieldObject(cfAuditProgram.getFieldId())
def config = cfAuditProgramField.getRelevantConfig(getIssueContext())
def optionManager = ComponentAccessor.getOptionsManager()
if (cfTypeofAuditValue == "Assessment") {
    def optionsForAP = optionManager.getOptions(config).findAll { it.value in ["VDA 6.3", "ASPICE"] }
    optionsForAP.collectEntries(optionMap) { option ->
        def optionValue = option.value
        [(option.optionId.toString()):optionValue]
    }
} else {
    def optionsForAP = optionManager.getOptions(config).findAll()
    optionsForAP.collectEntries(optionMap) { option ->
        def optionValue = option.value
        [(option.optionId.toString()):optionValue]
    }
}
cfAuditProgram.setFieldOptions(optionMap)
