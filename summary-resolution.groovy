def desc = getFieldByName("Summary of Issue After Resolution")

def defaultValue = """\
Problem Statement:
Was this is a miss in Initial validation? If Yes, then why did this happen?:
What will be done to ensure to avoid recurrence:
What is the root cause:
What is the fix:
When will the official fix be provided:
Official FW Version:

    """.stripIndent()

if (!desc.formValue) {
    desc.setFormValue(defaultValue)
}
