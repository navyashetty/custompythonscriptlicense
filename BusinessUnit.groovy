
import com.atlassian.jira.component.ComponentAccessor
import com.onresolve.jira.groovy.user.FieldBehaviours
import groovy.transform.BaseScript
import org.apache.log4j.Level
import org.apache.log4j.Logger

@BaseScript FieldBehaviours fieldBehaviours

def log = Logger.getLogger("com.wdc.scripts")
log.setLevel(Level.INFO)

def businessUnitProcessArea = [
        "Manufacturing":["Warehouse", "Finish Goods", "Production", "Engineering", "Kitting", "Die Bank", "Die W",
                         "Wafer Bank", "IQC", "OQA", "SMT", "Mechanical Assembly", "ORT", "Label & Packaging", "Rework",
                         "Test Engineering", "Non-Nett", "Routing", "Purge", "Change Control", "Sales Order", "KGD",
                         "System Test", "Singulation", "Ball Mount", "EVI", "SiP Failure Analysis / Reliability",
                         "SSD Failure Analysis / Reliability", "SiP Quality Engineering", "SSD Quality Engineering",
                         "Manufacturing Operations", "Debug", "Stencil / PCB Washing", "Automation",
                         "Magazine Cleaning", "Die Preparation", "Die Attach", "Wire Bond", "Mold", "Tape & Reel",
                         "BOP", "AOI", "COP", "Packing", "X-Ray", "Burn-In", "Memory Test", "Cleaning (PN)",
                         "Lube (PN)", "Sputter (PN)", "Test & Bagging (PN)", "Un-bagging and De-labelling (PN)",
                         "FQAP (PN)", "SPI (PN)", "Contamination Control", "eSPIM (PN)", "Operation Training (PN)",
                         "IE (SZ)", "QC", "Media MFG", "Tape MFG", "Prod. Planning", "MFG Training", "GP Plannning",
                         "Plate", "Group", "Polish", "Cleaning", "Test", "SPI", "Bag", "FQAP", "Manufacturing Ops",
                         "Headstack Operation", "Process Tech Thin Film", "Process Tech Wet/Metrology",
                         "Process Tech Mill/RIE", "Special Projects", "Assembly Process Engineering",
                         "Material Review Board", "Process Engineering", "T-Scan", "Laser Mark", "Laser Cut",
                         "Post Mold Cure", "Baking", "PVI", "FVI", "Lidding", "Lid Laser Mark", "EMI", "Post Plasma",
                         "Flip Chip", "Underfill", "Night Shift audit for all Assembly+ Test Processes", "FA Lab",
                         "Rel Lab", "Prime", "Rework/BB", "Metrology", "Quasi", "Center process", "Kitting/Packing",
                         "QA", "Vela ASM", "Vela TD", "Vela Center", "CR process", "Transporting process",
                         "GEN3 process", "LN2 process", "NEO process", "Dis-Assembly process", "Tester",
                         "BackEnd Tester", "WH/Store", "Tooling/ME Lab", "Realtime Lab", "Calibration Lab", "IDM Lab",
                         "Inventory Lab", "Test Lab", "DAS Lab", "Mechanical Lab", "Bonding/ACF/ACT Lab",
                         "Automation Lab", "Facility Lab", "Repair Lab", "Others-Engineering Lab", "PEC",
                         "Others-Non-Engineering Lab"],
        "RPG":["Program Management", "Systems", "HW", "FW", "Packaging", "PLC", "POM", "Validation",
               "Quality & Reliability"],
        "iNAND":["Program Management", "Systems", "HW", "FW", "Packaging", "PLC", "POM", "Validation",
                 "Quality & Reliability"],
        "eSSD":["Program Management", "Systems", "HW", "FW", "Packaging", "PLC", "POM", "Validation",
                "Quality & Reliability"],
        "cSSD":["Program Management", "Systems", "HW", "FW", "Packaging", "PLC", "POM", "Validation",
                "Quality & Reliability"],
        "ASIC":["Program Management", "Architecture", "ASIC Qualification & Chaz", "ASIC Design", "Systems", "HW",
                "FW", "Packaging", "PLC", "POM", "Validation", "Quality & Reliability"],
        "ASIC-PETE":["ASIC Reliability", "Test Plan, Test Program Development", "Others"],
        "Memory_Technology":["Program Management", "Systems", "HW", "FW", "Packaging", "PLC", "POM", "Validation",
                             "Quality & Reliability", "Applications Engineering", "Characterization",
                             "Silicon Technology Device Lab", "DAT", "NAND Reliability", "Process Device Integration",
                             "Memory Failure Analysis"],
        "Automotive":["FGI", "Label", "Packaging", "Cleanroom process", "Backend Test process", "Backend Process",
                      "Filler/Seeder process/software", "Cleanroom Rework process",
                      "Process Route", "OQA", "MST", "ORT", "Headstack Operation", "Production Planning", "NPI",
                      "Test Engineering", "Product Engineering/FA"],
        "C-enterprise":["FGI", "Label", "Packaging", "Cleanroom process", "Backend Test process", "Backend Process",
                        "Filler/Seeder process/software", "Cleanroom Rework process",
                        "Process Route", "OQA", "MST", "ORT", "Headstack Operation", "Production Planning", "NPI",
                        "Test Engineering", "Product Engineering/FA"],
        "P-enterprise":["FGI", "Label", "Packaging", "Cleanroom process", "Backend Test process", "Backend Process",
                        "Filler/Seeder process/software", "Cleanroom Rework process",
                        "Process Route", "OQA", "MST", "ORT", "Headstack Operation", "Production Planning", "NPI",
                        "Test Engineering", "Product Engineering/FA"],
        "Desktop":["FGI", "Label", "Packaging", "Cleanroom process", "Backend Test process", "Backend Process",
                   "Filler/Seeder process/software", "Cleanroom Rework process",
                   "Process Route", "OQA", "MST", "ORT", "Headstack Operation", "Production Planning", "NPI",
                   "Test Engineering", "Product Engineering/FA"],
        "Mobile":["FGI", "Label", "Packaging", "Cleanroom process", "Backend Test process", "Backend Process",
                  "Filler/Seeder process/software", "Cleanroom Rework process",
                  "Process Route", "OQA", "MST", "ORT", "Headstack Operation", "Production Planning", "NPI",
                  "Test Engineering", "Product Engineering/FA"],
        "Engineering":["NPI/PE", "Yield (PN)", "Yield/Charact (SZ)", "Analytical & Characterization Engineering (PN)",
                       "Failure Analysis (PN)", "Tribology (PN)", "Sputter",
                       "Wash/Cleaning", "Lube (PN)", "Lube&UV", "Test", "Backend", "Tape Eng.&Maint.", "Plate", "Group",
                       "Polish", "Cleaning", "Test  & Metrology", "QC",
                       "FA", "CC", "Pilot / PMI", "Process Development", "Post Sputter Process", "Sputter Process"],
        "FAB1-SLD Process":["Receiving/Warehousing/Logistics", "Incoming Inspection", "Split and Trim",
                            "Quad lap and Extender Lap", "Quad Bonding", "ABS Lap", "Fine Lap", "Oil Wash", "Back Lap",
                            "Row Slice", "Silicon Overcoat", "Wire Bond", "Solvent Clean"],
        "HMY-SLD Process":["ABS Carbon Coat", "AFM Measurement", "Robot Bond", "Planarization", "Photolithography",
                           "Ion Milling",  "Debond & Wash"],
        "Operations":["Slider Fabrication", "HGA", "Test"],
        "FAB2 SLD Process":["Cleaning", "ROWBAR Quasi", "Row Part", "Slider Wipe", "Inspection", "Wyko Flatness",
                            "Sort Debond", "Diakite Wash", "SDET-Jade", "Diakite Wash", "SDET INspection",
                            "OBA", "Packing & Shipping"],
        "HGA Process":["Parts Kitting", "HGA Auto Load/Unload", "ADU-Adhesive Dispensing Unit", "A2SK (Head Attach)",
                       "Inline Cure", "SBJET (Solder Ball Jet)", "HGA Auto Unload", "Static Altitude Test (SAT)",
                       "AUTO-WIPE", "ABS Inspection", "HGA Quasi", "Final Inspection", "Final HFE Cleaning",
                       "Quantity Check", "Packing", "Barcode", "Lot Checker", "FGI (Finish Goods)", "Shipping",
                       "Planning & Strategy" ],
        "Equipment and Mfg. System":["APC and Smart Mfg", "Equipment Engineering", "Big Data Analytics",
                                     "Project Management", "Process Excellence", "Sensor Equipment"],
        "IMS-GPO":["IMS-GPO", "Others"],
    	"SDET":["SDET/Amber Test", "SDET-Jade", "Diakite Wash", "SDET INspection", "OBA", "Packing & Shipping"],
        "Engineering and Integration":["Reader Process", "Reader Integration", "Writer Process", "Sensor Process",
                                       "Vacuum Process", "Wet Process", "Writer Mill", "Special Projects",
                                       "NPI and Integration", "Characterization", "Yield and FA"],
        "Support":["Procurement (PROC)", "Facilities", "Material Planning", "Production Planning", "IDM/MRO",
                   "Industrial Engineering", "NPI", "Real Estate Operations (REO)", "Human Resources (HR)",
                   "Supplier Quality", "Quality systems", "BPS", "POM", "PCM", "Legal", "Calibration", "Finance (FIN)",
                   "Customer Quality", "Failure Analysis (FA)", "Training", "Logistics", "Sales& Marketing", "Lab",
                   "Information Technology (IT)", "Software Tools", "EHS", "IMS", "Document Management", "Leadership",
                   "Change Management", "ET&A (PN)", "Inventory Management (PN)", "Health&Wellness/Clinic", "Security",
                   "PMC and Capital", "Data Analytics", "Quality Assurance (QA)", "Head Development(HDEV)", "SCM",
                   "Design Of Experiment (DOE)", "Equipment Maintenance", "Spares & Repair Center",  "Site Services",
                   "Material Science Laboratory (MSL)", "Manufacturing Operations (MO)", "Production Control",
                   "Manufacturing Engineering (ME)", "Manufacturing Excellence & Technical projects (MEx & TP)",
                   "Product Management/Product Engineering (PM/PE)", "Production Control (SZ)", "Development",
                   "Production Control/ IE (PN)", "Management Review", "Customer Sales & Services",
                   "Warranty Operations", "BCMS"]

]

def cfProcessArea = getFieldByName("Process Area")
def cfBusinessUnit = getFieldByName("Business Unit")
def cfBusinessUnitValue = cfBusinessUnit.getValue() as String

def optionMap = new LinkedHashMap<String, String>()
optionMap["-1"] = "None"

if (cfBusinessUnitValue ) {
    def paOptionValues =  businessUnitProcessArea.get(cfBusinessUnitValue)
    def customFieldManager = ComponentAccessor.customFieldManager
    def cfProcessAreaField = customFieldManager.getCustomFieldObject(cfProcessArea?.fieldId)
    def paCFConfig = cfProcessAreaField?.getRelevantConfig(issueContext)
    def optionManager = ComponentAccessor.optionsManager
    def allOptions = optionManager.getOptions(paCFConfig)
    def optionsForRL = allOptions .findAll { it.value in paOptionValues || it.value == "Others" }

    optionsForRL.collectEntries(optionMap) { option ->
        def optionValue = option.value
        [(option.optionId.toString()):optionValue]
    }
}

cfProcessArea.setFieldOptions(optionMap)
cfProcessArea.setRequired(true)
