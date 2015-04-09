package com.simplejsonparser

object Main {

  def main(args: Array[String]) {
	
    val m = new SimpleJsonParser()
    			.parseJsonObject("""{"a":[{"pepe":12.23,"ce":false,"pepe3":true,"nene":{"na":"ne  fd "}},123]}""");
	val j = new J(m)
	
	println(j("a")(0)("pepe3").get[Boolean] && true)
	
	val j2 = new SimpleJsonParser()
	.parseJson("""["gola",{"pa":"pe","ce":{"na":{"n i":{"no":"n  u"}}}}]""");
	println(j2(1)("ce")("na")("n i")("no")
	)
	val a = System.currentTimeMillis();
	val j3 = new SimpleJsonParser().parseJson("""{"user_local_timestamp":"2015-03-14T15:17:03.566-0300","application":{"site_id":"MLA","app_id":"7092","business":"mercadolibre","version":"4.3.3"},"user_time":1426357023566,"secure":false,"server_time":1427463733961,"event_data":{"item_id":"MLA549658349","failed":false,"context":"\/vip","question_id":"3483272095","mode":"normal"},"catalog_data":{"is_valid":false,"version":40},"type":"event","device_time":1427463805128,"id":"74021548-612d-4802-b460-9ae7b641ee26","user_timestamp":"2015-03-14T18:17:03.566+0000","priority":"NORMAL","path":"\/questions\/ask\/post","device":{"connectivity_type":"EDGE","orientation":0.0,"platform":"\/mobile\/android","device_name":"GT-S5301L","device_id":"6c28d87ca8fccdbe","resolution_height":320.0,"resolution_width":240.0,"os_version":"4.0.4"},"server_timestamp":"2015-03-27T13:42:13.961+0000","retry":0,"user":{"uid":"f3f62930-6e4c-4335-82b3-5a9542bd3d1a-n","user_id":"32599461"},"device_timestamp":"2015-03-27T13:43:25.128+0000"}""")
	println(System.currentTimeMillis() - a)
	println(j3("application")("site_id"))
	println(j3("user_local_timestamp"))
  }
  
  
  
}