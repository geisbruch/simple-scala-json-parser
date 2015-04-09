package com.simplejsonparser
import java.text.ParseException
import com.simplejsonparser.JsonObjectElement

class SimpleJsonParser {
	
	
	var charPos = 0;
	
	var state: String = "begin";
	
	var currentElement: JsonElement = null;
	
	def parseAll(str:String) = {
	   var iterations = 0;
	   while(charPos < str.length()) {
	      iterations = iterations+1;
	      if(iterations > str.length()*2){
	        throw new Exception("Error parsing ["+str+"]")
	      }
		  str(charPos) match {
		    case '{' if state == "begin" => {
		    	currentElement = new JsonObjectElement()
		    	charPos = charPos+1;
		    	state = "parse_key"
		    }
		    case '{' if state == "parse_value" => {
		    	var elem : JsonObjectElement = new JsonObjectElement();
		    	elem.setParent(currentElement);
		    	currentElement = elem;
		    	charPos = charPos+1;
		    	state = "parse_key"
		    }
		    case '[' if state == "parse_value" => {
		      charPos = charPos+1;
		      var elem : JsonArrayElement = new JsonArrayElement();
		      elem.setParent(currentElement);
		      currentElement = elem;
		      state = "parse_value"
		    }
		    case '"' if state == "parse_key" => {
		    	val key: String = parseString(str);
		    	currentElement.addElement(key, state);
		    	state = "wait_semicolon";
		    }
		    case '"' if state == "parse_value" => {
		    	val value: String = parseString(str);
		    	currentElement.addElement(value, state);
		    }
		     case ':' if state == "wait_semicolon" => {
		       state = "parse_value"
		       charPos = charPos+1;
		    }
		    case ']' if state == "parse_value" => {
		      currentElement.finishElement();
		      var e: JsonElement = currentElement.getParent();
		      if(e!= null) {
		        currentElement = e;
		      }
		      charPos = charPos+1;
		    }
		    case '}' if state == "parse_value" || state == "parse_key" => {
		      state = "parse_value"
		      currentElement.finishElement();
		      var e: JsonElement = currentElement.getParent();
		      if(e!= null) {
		        currentElement = e;
		      }
		      charPos = charPos+1;
		    }
		    case ',' if state == "parse_value" => {
		    	charPos = charPos+1;
		    	state = currentElement.stateAfterValue();
		    }
		    case ' ' => charPos = charPos+1;
		    case c if state == "parse_value" => {
		      var n: Any = parseSpecial(str);
		      currentElement.addElement(n, state);
		    }
		    case a => throw new ParseException("Char ["+a+"] invalid at pos ["+charPos+"] on json ["+str+"]",charPos);
		  }
	  }
	}
	
	def parseJsonObject(str: String): Map[String, Any] = {
		str(charPos) match {
		    case '{' if state == "begin" => {
		    	currentElement = new JsonObjectElement()
		    	charPos = charPos+1;
		    	state = "parse_key"
		    }
		    case default => throw new ParseException("["+str+" is not a json object]",0)
		}
		parseAll(str);
	  return (currentElement.asInstanceOf[JsonObjectElement]).getMap.toMap;
	}
	
	def parseJsonArray(str: String): Array[Any] = {
		str(charPos) match {
		    case '[' if state == "begin" => {
		    	currentElement = new JsonArrayElement()
		    	charPos = charPos+1;
		    	state = "parse_value"
		    }
		    case default => throw new ParseException("["+str+" is not a json array]",0)
		}
		parseAll(str);
	  return (currentElement.asInstanceOf[JsonArrayElement]).getArr;
	}
	
	def parseJson(str : String): J = {
	  str(charPos) match {
		    case '[' => new J(parseJsonArray(str))
		    case '{' => new J(parseJsonObject(str))
	  }
	}
	
	def parseSpecial(str : String) : Any = {
	  try {
		  val strBuilder: StringBuilder = new StringBuilder();
		  while(charPos < str.length() && 
		      str(charPos) != ',' &&
		      str(charPos) != ']' &&
		      str(charPos) != '}') {
		    strBuilder.append(str(charPos));
		    charPos = charPos + 1;
		  }
		  val dStr = strBuilder.toString;
		  return dStr match {
		    case "true" => true
		    case "false" => false
		    case "null" => null
		    case default => {
		    	val dVal = dStr.toDouble
				if(dVal == dVal.longValue) {
				  return dVal.longValue
				} else {
				  return dVal
				}
		    }
		  }
	  } catch { 
	    case e: NumberFormatException => throw new Exception("Error parsing ["+str+"]",e);
	  }
	  
	}
	
	def parseString(str : String) : String = {
	  var special = false;
	  val strBuilder: StringBuilder = new StringBuilder();
	  charPos = charPos+1;
	  while(charPos < str.length()) {
	    str(charPos) match {
	      case '"' if !special => {
	    	  charPos = charPos+1;
	    	  return strBuilder.toString();
	      }
	      case c => {
	    	  strBuilder.append(c);
	    	  if(c == '\\' && !special) {
	    	    special = true
	    	  } else {
	    		  special = false;
	    	  }
	      }
	    }
	    charPos = charPos+1;
	  }
	  throw new ParseException("Error parsing string", charPos)
	}
	
	
}