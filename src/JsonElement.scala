trait JsonElement {

  def getParent() : JsonElement;
  
  def setParent(element: JsonElement);
  
  def addElement(element: Any, status: String);
  
  def finishElement()
  def stateAfterValue() : String;
}