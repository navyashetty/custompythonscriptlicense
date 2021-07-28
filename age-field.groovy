import com.atlassian.jira.component.ComponentAccessor

def resolutionDate = issue.getResolutionDate()
def createdDate = issue.getCreated()
def currentDate = new Date()
def customFieldManager = ComponentAccessor.getCustomFieldManager()
def cfReportedDate = customFieldManager.getCustomFieldObjects(issue).find { it.name == 'Reported Date' }
def cfReportedDateValue = issue.getCustomFieldValue(cfReportedDate) as Date
def aging = 0

if (resolutionDate) {
    if (cfReportedDateValue) {
        aging = (resolutionDate - cfReportedDateValue)
	 } else {
    	   aging = (resolutionDate - createdDate)
    }
} else {
    if (cfReportedDateValue) {
        aging = (currentDate - cfReportedDateValue)
	 } else {
    	   aging = ((currentDate - createdDate))
    }
}
return aging as Double
