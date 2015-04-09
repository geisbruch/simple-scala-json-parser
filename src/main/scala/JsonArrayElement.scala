class JsonArrayElement extends JsonElement{
  var parent : JsonElement = null;
  
  var eArr: Array[Any] = Array();
  
  def getParent() : JsonElement = {
    return parent;
  }
  
  def setParent(element: JsonElement) = {
    parent = element;
  }
  
  def addElement(element: Any, status: String) = {
	  eArr = eArr ++ Array(element);
  }
  
  def getArr() : Array[Any] = {
    return eArr
  }
  
  def finishElement() = {
    if(parent != null) {
	    parent.addElement(eArr, "parse_value");
	  }
  }
  def stateAfterValue() : String = {
     return "parse_value"
  }
  
}