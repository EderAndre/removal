package br.gov.rs.defensoria.removal.report

import java.text.SimpleDateFormat

import br.gov.rs.defensoria.removal.report.presentation.ResultTableWinnersWithPretensions

class ExportPretensionsToXML {
    public def widthReport=520
    public def widthDinamicColumns=11
    public def heightReport=500
    public def marginLeft=20
    public def marginRight=20
    public def marginTop=20
    public def marginBottom=20
    public def plotArea=0
    public def dinamicFields=''
    public def dinamicColumns=0
    public def dateResult
    public def datePrint=new SimpleDateFormat('dd/MM/yyyy HH:mm:ss').format(new Date())
    public def imagePath=''
    public def logoDPE='logo_dpe_txt.png'
    public def imgStarOn='star-on.png'
    public def imgStarOff='star-off.png'

    def generateStringXML(ResultTableWinnersWithPretensions resultTable,String closedDate) {

        imagePath=this.getClass().getResource('/images/')
        dateResult=closedDate
        String xmlTemplate = this.getClass().getResource('/jasperTemplates/removalReport.template').getText("UTF-8")
        def counter=0
        List<String> registerFields = []
        resultTable.officesList.each { office ->
            registerFields.add("<field name='${office.name.trim()}' class='java.lang.String'/>".replaceAll('º', 'o').replaceAll('ª', 'a'))
            widthReport += widthDinamicColumns
            counter++
        }
        dinamicColumns=counter
        dinamicFields=registerFields.join('\n\t')

        String result= xmlTemplate.replaceAll('__HEIGHT_REPORT',heightReport.toString())
                .replaceAll('__MARGIN_LEFT',marginLeft.toString() )
                .replaceAll('__MARGIN_RIGHT',marginRight.toString() )
                .replaceAll('__MARGIN_TOP',marginTop.toString() )
                .replaceAll('__MARGIN_BOTTOM',marginBottom.toString() )
                .replaceAll('__DATE_RESULT',closedDate)
                .replaceAll('__DATE_PRINT',datePrint.toString())
                .replaceAll('__IMG_LOGO',this.getClass().getResource('/images/'+logoDPE).toString())
                .replaceAll('__IMG_STAR_ON',this.getClass().getResource('/images/'+imgStarOn).toString())
                .replaceAll('__IMG_STAR_OFF',this.getClass().getResource('/images/'+imgStarOff).toString())
                .replaceAll('__DINAMIC_FIELDS',dinamicFields)
                .replaceAll('__DINAMIC_HEADERS',this.generateHeader(resultTable))
                .replace('__DINAMIC_TEXT_FIELDS',this.generateTextField(resultTable))
                .replace('__DINAMIC_STYLES',this.generateDinamicStyles(resultTable))
                .replaceAll('__PLOT_AREA',(plotArea=widthReport-marginLeft-marginRight).toString() )
                .replaceAll('__WIDHT_REPORT',widthReport.toString())
        return result
    }
    private String generateDinamicStyles(ResultTableWinnersWithPretensions resultTable) {
        int iterator8=0
        List<String> dinamicStyles = []
        String stylesText=""
        resultTable.officesList.each { office -> dinamicStyles.add([office.name, office.hasOwner, '']) }
        String stylesModel=this.getClass().getResource('/jasperTemplates/conditionalStyles.template').text
        dinamicStyles.each {
            def correction4="${it[0]}".replaceAll( 'º', 'o').replaceAll( 'ª', '').trim()
            stylesText +=stylesModel.replaceAll("__CONDITIONAL_STYLE", "column_"+iterator8.toString()).replaceAll("__FIELD_NAME", correction4)
            iterator8++
        }
        return stylesText
    }
    private String generateTextField(ResultTableWinnersWithPretensions resultTable) {
        def iterator3=0
        def iterator4=0
        List<String> textFields = []
        String resultText=""
        String textFieldModel=this.getClass().getResource('/jasperTemplates/modelTextField.template').getText("UTF-8")
        resultTable.officesList.each { office -> textFields.add([office.name, office.hasOwner])}
        textFields.each{
            String correction2="${it[0]}".replaceAll( 'º', 'o').replaceAll( 'ª', '').trim()
            resultText += textFieldModel.replaceAll("__NAME_TEXT_FIELD", correction2)
                    .replaceAll('__X_POSITION', (480+iterator4).toString())
                    .replaceAll('__CONDITIONAL_STYLE', "column_"+iterator3.toString())
                    .replaceAll('__WIDTH_DINAMIC_COLUMNS', widthDinamicColumns.toString())
            iterator4=iterator4+widthDinamicColumns
            iterator3++
        }
        return resultText
    }
    private String generateHeader(ResultTableWinnersWithPretensions resultTable) {
        int iterator1=0
        int iterator2=0
        List<String> headerColumns = []
        String columnHeader=""
        resultTable.officesList.each { office -> headerColumns.add([office.name, office.hasOwner, '']) }
        String columnHeaderModel=this.getClass().getResource('/jasperTemplates/modelHeader.template').getText("UTF-8")
        String imageFlags=""
        headerColumns.each {
            String correction=new Utils().abreviarNome("${it[0]}".replace( 'º', 'o').replaceAll( 'ª', '').trim())
            columnHeader +=columnHeaderModel.replaceAll("__X_POSITION", (480+iterator1).toString())
                    .replaceAll("__NAME_HEADER", correction)
                    .replaceAll("__WIDTH_DINAMIC_COLUMNS", widthDinamicColumns.toString())

            if(it[1]==false){
                imageFlags += this.definitionFlag(1,iterator1+480)
            }
            else{
                def thisOffice=it[0]
                def winning=resultTable.rows.find { it.originOffice==thisOffice }?.wonOffice
                if(winning){
                    imageFlags += this.definitionFlag(2,iterator1+480)
                }
                else if(!winning){
                    imageFlags+=this.definitionFlag(3, iterator1+480)
                }
            }
            iterator1=iterator1+widthDinamicColumns
            iterator2++
        }
        return columnHeader+imageFlags
    }
    private String definitionFlag(int statusHeader, int positionX) {
        imagePath=this.getClass().getResource('/images/')
        String flagImage=this.getClass().getResource('/jasperTemplates/imageHeader.template').getText("UTF-8")
        flagImage=flagImage.replaceAll("__X_POSITION",positionX.toString())
                .replaceAll("__FLAG_STATUS",imagePath.toString()+"status"+statusHeader.toString()+".png")
                .replaceAll('__WIDTH_DINAMIC_COLUMNS', widthDinamicColumns.toString())
        return flagImage
    }
   def export(ResultTableWinnersWithPretensions resultTable, String closedDate, String localPath, String fileName) {
        def result=this.generateStringXML(resultTable,closedDate)
        new File(localPath+fileName+'.jrxml' ).withWriter("UTF-8") {	it.print result}
        return localPath+fileName+'.jrxml'
    }
}
