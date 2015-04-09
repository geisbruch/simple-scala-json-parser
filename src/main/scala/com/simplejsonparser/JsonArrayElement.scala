package com.simplejsonparser

class JsonArrayElement extends JsonElement{
  var parent : JsonElement = null;
  
  var eArr: scala.collection.mutable.ArrayBuffer[Any] = new scala.collection.mutable.ArrayBuffer[Any];
  
  def getParent() : JsonElement = {
    return parent;
  }
  
  def setParent(element: JsonElement) = {
    parent = element;
  }
  
  def addElement(element: Any, status: String) = {
	  eArr +=element
  }
  
  def getArr() : Array[Any] = {
    return eArr.toArray
  }
  
  def finishElement() = {
    if(parent != null) {
	    parent.addElement(eArr.toArray, "parse_value");
	  }
  }
  def stateAfterValue() : String = {
     return "parse_value"
  }
  
}