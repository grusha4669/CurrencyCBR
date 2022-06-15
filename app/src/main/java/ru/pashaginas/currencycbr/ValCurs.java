package ru.pashaginas.currencycbr;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

@Root(name="ValCurs")
public class ValCurs {

   @ElementList(inline = true)
   List<Valute> valuteList;

   @Attribute(name = "Date")
   private String date;

   @Attribute(name = "name")
   private String name;

   public String getDate() {
      return date;
   }

   public void setDate(String date) {
      this.date = date;
   }

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   @Override
   public String toString() {
      return "ClassPojo [Date = " + date + ", name = " + name + ", Valute = " + "Valute" + "]";
   }
}
