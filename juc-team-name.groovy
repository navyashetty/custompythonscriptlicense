def cfTeamName = getFieldByName("Team Name")
def cfSandiskProject = getFieldByName("Sandisk Project")
def cfTeamNameValue = cfTeamName.getValue() as String

if (cfTeamNameValue == "SANDISK") {
    cfSandiskProject.setHidden(false)
    cfSandiskProject.setRequired(true)
} else {
    cfSandiskProject.setRequired(false)
    cfSandiskProject.setHidden(true)
    cfSandiskProject.setFormValue("")
}

