def cfUserType = getFieldByName("User Type")
def cfEmailIds = getFieldByName("Email IDs")
def cfVendorEmail = getFieldByName("Vendor Email")
def cfVendorName = getFieldByName("Vendor Name")
def cfVendorUserType = getFieldByName("Vendor User Type")
def cfSnowTicket = getFieldByName("SNOW Ticket No")
def cfUserTypeValue = cfUserType.getValue() as String

if (cfUserTypeValue == "Vendor User") {
    cfVendorEmail.setRequired(true)
    cfVendorName.setRequired(true)
    cfVendorEmail.setHidden(false)
    cfVendorName.setHidden(false)
    cfEmailIds.setHidden(true)
    cfEmailIds.setRequired(false)
    cfVendorUserType.setHidden(false)
    cfVendorUserType.setRequired(true)
    cfSnowTicket.setHidden(true)
    cfSnowTicket.setRequired(false)
} else if (cfUserTypeValue == "Internal WDC User") {
    cfEmailIds.setHidden(false)
    cfEmailIds.setRequired(true)
    cfVendorEmail.setHidden(true)
    cfVendorName.setHidden(true)
    cfVendorEmail.setRequired(false)
    cfVendorName.setRequired(false)
    cfVendorUserType.setHidden(true)
    cfVendorUserType.setRequired(false)
    cfSnowTicket.setHidden(true)
    cfSnowTicket.setRequired(false)
}
