import com.intellij.database.model.DasTable
import com.intellij.database.model.ObjectKind
import com.intellij.database.util.Case
import com.intellij.database.util.DasUtil


import java.sql.Date


/*
 * Available context bindings:
 *   SELECTION   Iterable<DasObject>
 *   PROJECT     project
 *   FILES       files helper
 */

packageName = "com.zws.api.service;"

typeMapping = [
        (~/(?i)tinyint|smallint|mediumint/)      : "Integer",
        (~/(?i)int/)                             : "Long",
        (~/(?i)bool|bit/)                        : "Boolean",
        (~/(?i)float|double|decimal|real/)       : "Double",
        (~/(?i)datetime|timestamp|date|time/)    : "Date",
        (~/(?i)blob|binary|bfile|clob|raw|image/): "InputStream",
        (~/(?i)number/)                          : "Integer",
        (~/(?i)/)                                : "String"
]


FILES.chooseDirectoryAndSave("Choose directory", "Choose where to store generated files") { dir ->
  SELECTION.filter { it instanceof DasTable && it.getKind() == ObjectKind.TABLE }.each { generate(it, dir) }
}


def generate(table, dir) {
  //def className = javaClassName(table.getName(), true)
  def className = javaName(table.getName(), true)
  def fields = calcFields(table)
  //当是maven项目的时候，需要解开下面的注释
//  packageName = getPackageName(dir)
  new File(dir, className + "Service.java").withPrintWriter { out -> generate(out, className, fields, table) }
}


def generate(out, className, fields, table) {


  out.println "package $packageName"
  out.println ""
  out.println "import com.zws.api.model.$className;"
  out.println "import org.springframework.data.domain.Page;"
  out.println "import org.springframework.data.domain.Pageable;"
  out.println "import java.util.Optional;"
  out.println ""
  out.println "public interface ${className}Service {"
  out.println ""
  out.println ""
  out.println "/**"
  out.println "* @param id"
  out.println "* @return"
  out.println "*/"
  out.println ""
  out.println "Optional<$className> findById(Long id);"
  out.println ""
  out.println ""
  out.println "/**"
  out.println "* @param ${className.toLowerCase()}"
  out.println "* @param page"
  out.println "* @param pageSize"
  out.println "* @return"
  out.println "*/"
  out.println ""
  out.println "Page<${className}> findByPage(${className} ${className.toLowerCase()} , Integer page, Integer pageSize);"
  out.println ""
  out.println ""
  out.println "/**"
  out.println "* @param ${className.toLowerCase()}"
  out.println "* @return"
  out.println "*/"
  out.println ""
  out.println "$className insertBy$className($className ${className.toLowerCase()});"
  out.println ""
  out.println ""

  out.println "/**"
  out.println " *"
  out.println " * @param ${className.toLowerCase()}"
  out.println "  * @return"
  out.println " */"
  out.println " Optional<${className}>  updateBy${className}(Long id, ${className} ${className.toLowerCase()});"
  out.println ""
  out.println " /**"
  out.println "  *"
  out.println "  * @param id"
  out.println "  */"
  out.println " void deleteById(Long id);"

  out.println ""
  out.println "}"

}

def calcFields(table) {
  DasUtil.getColumns(table).reduce([]) { fields, col ->
    def spec = Case.LOWER.apply(col.getDataType().getSpecification())

    def typeStr = typeMapping.find { p, t -> p.matcher(spec).find() }.value
    def comm = [
            colName : col.getName(),
            name    : changeStyle(javaName(col.getName(), false), true),
            type    : typeStr,
            commoent: col.getComment(),
            flag: " ",
            annos   : "\t@Column(name = \"" + col.getName() + "\" )"]
    if ("id".equals(Case.LOWER.apply(col.getName())))
      comm.flag += "@Id"
    else
      comm.flag += "@Basic"

    fields += [comm]
  }
}



def javaName(str, capitalize) {
  def s = str.split(/(?<=[^\p{IsLetter}])/).collect { Case.LOWER.apply(it).capitalize() }
          .join("").replaceAll(/[^\p{javaJavaIdentifierPart}]/, "_")
  capitalize || s.length() == 1 ? s : Case.LOWER.apply(s[0]) + s[1..-1]
}

def isNotEmpty(content) {
  return content != null && content.toString().trim().length() > 0
}

static String changeStyle(String str, boolean toCamel) {
  if (!str || str.size() <= 1)
    return str

  if (toCamel) {
    String r = str.toLowerCase().split('_').collect { cc -> Case.LOWER.apply(cc).capitalize() }.join('')
    return r[0].toLowerCase() + r[1..-1]
  } else {
    str = str[0].toLowerCase() + str[1..-1]
    return str.collect { cc -> ((char) cc).isUpperCase() ? '_' + cc.toLowerCase() : cc }.join('')
  }
}

static String genSerialID() {
  return "\tprivate static final long serialVersionUID =  " + Math.abs(new Random().nextLong()) + "L;";
}
