# dgjianke-framework
附上maven的相关配置
<?xml version="1.0" encoding="UTF-8"?>


<settings xmlns="http://maven.apache.org/SETTINGS/1.0.0" 
          xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" 
          xsi:schemaLocation="http://maven.apache.org/SETTINGS/1.0.0 http://maven.apache.org/xsd/settings-1.0.0.xsd">
 
<localRepository>H:\local_maven_respository</localRepository>

  <pluginGroups>
   <pluginGroup>com.juvenxu.mvnbook.account</pluginGroup>
   <pluginGroup>org.apache.tomcat.maven</pluginGroup>
   <pluginGroup>org.codehaus.mojo</pluginGroup>
   <pluginGroup>org.mybatis.generator</pluginGroup>
  </pluginGroups>
  
  
  <proxies>
	<proxy>
		<id>my-proxy</id>
		<active>false</active> <!-- 如果配置了多个proxy，则靠这个属性来确定激活当前哪一个 -->
		<protocol>http</protocol>
		<host>proxy.ah.cmcc</host>
		<port>8080</port>
		<username>***</username>
		<password>***</password>
		<nonProxyHosts>localhost|127.0.0.1|10.*.*.*</nonProxyHosts> <!-- 这里面配置的皆是不需要代理就可以访问的IP地址，如仓库在内网和本机的这种情况 -->
	</proxy>
  </proxies>


  <servers>

	 <server>
		<id>nexus-release</id>
		<username>deployment</username>
		<password>111111</password>
	 </server>
	 <server>
		<id>nexuse-snapshots</id>
		<username>deployment</username>
		<password>111111</password>
	 </server>
    
  </servers>

  
  <mirrors>
	<mirror>
		<id>dgjianke_repository</id>
		<name>dgjianke nexus</name>
		<url>http://localhost:8081/nexus/content/groups/public/</url>
		<mirrorOf>*,!spring-snasphot,!mavenAndroid,!googlecode-ksoap2-android,!java.net</mirrorOf>
		
	</mirror>
  </mirrors>
  
 
 <profiles>
  </profiles>



</settings>
