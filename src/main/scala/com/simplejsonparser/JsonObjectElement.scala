package com.simplejsonparser

class JsonObjectElement extends JsonElement {

  var map : scala.collection.mutable.Map[String, Any] = scala.collection.mutable.Map();
  
  var lastKey: String = null;
  
  var parent : JsonElement = null;
  
  def getParent() : JsonElement = {
    return parent;
  }
  
  def setParent(element: JsonElement) = {
    parent = element;
  }
  
  def addElement(element: Any, status: String) = {
    status match {
      case "parse_key" => lastKey = element.toString()
      case "parse_value" => map.put(lastKey, element)
    }
  };
  
  def getMap : scala.collection.mutable.Map[String, Any] = {
    return map;
  }
  
  def finishElement() = {
	  if(parent != null) {
	    parent.addElement(map.toMap, "parse_value");
	  }
  }
  
  def stateAfterValue() : String = {
     return "parse_key"
  }
}