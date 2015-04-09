package com.simplejsonparser

class J(val v : Any, val map : Map[String, Any], val arr : Array[Any]) 
	extends java.io.Serializable with java.lang.Cloneable{

  
  def this(arr : Array[Any]) = {
	  this(arr, null, arr);
  }
  
  def this(map : Map[String, Any]) = {
	  this(map, map, null)
  }
  
  def apply(k : String) : J = {
    if(!map.contains(k))
      return null;
    return getJ(map(k)) 
  }
  
  def apply(i : Integer) : J = {
    return getJ(arr(i)) 
  }
  
  def getJ(e : Any) = e match {
  	case e: Array[Any] => new J(e.asInstanceOf[Array[Any]])
    case e: Map[String, Any] => new J(e.asInstanceOf[Map[String, Any]])
    case e: Any => new J(e, null, null)
  }
  
  def get[T] : T = {
    return v.asInstanceOf[T]
  }
  
  override def toString() : String = {
    return v.toString();
  }
}