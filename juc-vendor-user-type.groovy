def cfVendorUserType = getFieldByName("Vendor User Type")
def cfSnowTicket = getFieldByName("SNOW Ticket No")
def cfVendorUserTypeValue = cfVendorUserType.getValue() as String

if (cfVendorUserTypeValue == "New Vendor User") {
    cfSnowTicket.setHidden(false)
    cfSnowTicket.setRequired(true)
} else if (cfVendorUserTypeValue == "Existing Vendor User") {
    cfSnowTicket.setHidden(true)
    cfSnowTicket.setRequired(false)
}
