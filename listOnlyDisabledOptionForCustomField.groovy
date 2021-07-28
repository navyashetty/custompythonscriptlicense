import com.atlassian.jira.component.ComponentAccessor
import com.atlassian.jira.project.Project

def projectManager = ComponentAccessor.getProjectManager()
List<Project> allProjects = []
// Enter the desired project key
def projectName = "LRMS"
if (projectName != "") {
    allProjects.add(projectManager.getProjectObjByKey(projectName))
} else {
    allProjects = projectManager.getProjectObjects()
}

allProjects.each { project ->
    def projectId = project?.id
    log.warn("Project Key:= ${project.key} -> Project ID:= ${projectId}")
    def customFieldManager = ComponentAccessor.getCustomFieldManager()
    def allCFs = customFieldManager.getCustomFieldObjects(projectId, [])
    allCFs.each { cf ->
        def cfType = cf.getCustomFieldType().getName()
        if (cfType == "Select List (single choice)") {
            def optionsManager = ComponentAccessor.getOptionsManager()
            def allDisabledOptions = optionsManager.getOptions(cf.getConfigurationSchemes().
                                                               listIterator().next().
                                                               getOneAndOnlyConfig()).findAll { it.disabled }
            if (allDisabledOptions.size() >= 1) {
                log.warn("Field Name:= ${cf.getFieldName()}  -> Field Type:= ${cfType}")
                log.warn("Disabled options are:=  ${allDisabledOptions}")
            }
        }
    }
}
