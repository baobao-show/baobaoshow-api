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

packageName = "com.zws.api.service.impl;"

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
  new File(dir, className + "ServiceImpl.java").withPrintWriter { out -> generate(out, className, fields, table) }
}


def generate(out, className, fields, table) {


  out.println "package $packageName"
  out.println ""
  out.println "import com.zws.api.utils.SpecificationFactory;"
  out.println "import com.zws.api.model.${className};"
  out.println "import com.zws.api.repo.${className}Dao;"
  out.println "import com.zws.api.service.${className}Service;"
  out.println "import org.springframework.beans.factory.annotation.Autowired;"
  out.println "import org.springframework.cache.annotation.Cacheable;"
  out.println "import org.springframework.data.domain.Page;"
  out.println "import org.springframework.data.domain.Pageable;"
  out.println "import org.springframework.data.jpa.domain.Specification;"
  out.println "import org.springframework.stereotype.Service;"
  out.println "import java.util.Optional;"
  out.println "import org.springframework.data.domain.Sort;"
  out.println ""
  out.println ""
  out.println "@Service"
  out.println "public class ${className}ServiceImpl implements ${className}Service {"
  out.println ""
  out.println ""

  out.println "@Autowired"
  out.println "${className}Dao ${className.toLowerCase()}Dao;"


  out.println ""
  out.println "@Override"
  out.println "@Cacheable(value = \"${className.toLowerCase()}\", key = \"'id_'+#id\",  unless = \"#result eq null\")"
  out.println "public Optional<${className}> findById(Long id){"
  out.println "   return  ${className.toLowerCase()}Dao.findById(id);"
  out.println "}"
  out.println ""
  out.println ""
  out.println "@Override"
  out.println "public ${className} insertBy${className}(${className} ${className.toLowerCase()}) {"
  out.println "   return ${className.toLowerCase()}Dao.save(${className.toLowerCase()});"
  out.println "}"

  out.println ""
  out.println ""
  out.println "@Override"
  out.println "public void deleteById(Long id) {"
  out.println "  ${className.toLowerCase()}Dao.deleteById(id);"
  out.println "}"

  out.println ""
  out.println ""
  out.println "@Override"
  out.println "public Optional< ${className}>  updateBy${className}(Long id,  ${className} ${className.toLowerCase()}){"

  out.println "${className.toLowerCase()}Dao.save(${className.toLowerCase()});"

  out.println "return ${className.toLowerCase()}Dao.findById(id);"


  out.println "}"
  out.println ""
  out.println "@Cacheable(value = \"pages\", key = \"'page_'+#page+'_'+#pageSize\",  unless = \"#result eq null\")"
  out.println "public Page<${className}> findByPage(${className} ${className.toLowerCase()}, Integer page, Integer pageSize) {"


  out.println "  Specification<${className}> spec = Specification"
  out.println "          .where(SpecificationFactory.containsLike(\"name\",${className.toLowerCase()}.getName()));"


  out.println "  page = page <= 1 ? 0 : page -1;// 如果page为负数则修改为0，防止在首页点击上一页发生错误"
  out.println "  Sort sort = new Sort(Sort.Direction.DESC, \"id\");// 按id倒叙排列"


  out.println " Pageable pageable = new PageRequest(page, pageSize, sort);"

  out.println " return  ${className.toLowerCase()}Dao.findAll(spec,pageable);"

  out.println "}"
  out.println ""
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
