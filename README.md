<h1>Depth Charts</h1>

Depth chart is a REST API base application use to maintain sports, players and the ranks each for the position.
<h2>Assumptions</h2>
<ul>
<li>
 Adding players to the chart needs to be sequential depth.
</li>
</ul>

<h2>Application</h2>

<h3>Prerequisites</h3>
The application runs on the following configurations:
* Java 8
* Maven

<h3>Configurations</h3>
The application needs to support a sport before the application runs. 
This is configured in the file located at: <i>resources/application.properties</i> by the 
property key <b>depth.chart.sports</b>.
</br>
e.g. 
</br>
<code>
depth.chart.sports={NFL:5,BB:3}
</code>

<b>Note:</b> 
    NFL is a sport it supports to and maximum depth of a chart is set to 5. 
    BB is a sport it supports to and maximum depth of a chart is set to 3. 

This application supports NFL and BB and holds respective figure as a maximum depth for the chart. 
Any player added beyond the maximum depth is rejected/removed from the chart.

<h3>Build Application</h3>
<code>
mvn install
</code>

<h3>Running Application</h3>
<code>
mvn spring-boot:test
</code>

<h3>REST API URLS</h3>
<b>NOTE:</b> Every url need to accompany the sport which the application supports.
Defined below for NFL operations. This code needs to be consist with the configuration in the application.properties

<h4>Create Single Player</h4>
<code>
POST http://localhost:8080/depth-chart/NFL/player </br>
&nbsp;&nbsp;{ "number": 4, name": "Scott Miller"}</br>
</code>

<h4>Create Multiple Players</h4>
<code>
POST http://localhost:8080/depth-chart/NFL/players </br>
[</br>
&nbsp;&nbsp;{ "number": 1, "name": "Jaelon Darden" }, </br>
&nbsp;&nbsp;{ "number": 2, "name": "Kyle Trask" },</br>
&nbsp;&nbsp;{ "number": 3, name": "Tom Bradly"}</br>
]
</code>

<h4>Add Player To Chart</h4>
<code>
POST http://localhost:8080/depth-chart/NFL/player/addToChart </br>
&nbsp;&nbsp;{  "position": "QB", "playerId": 1,  "positionInDepth": 1}</br>
</code>

<h4>Add Multiple Players To The Chart</h4>
<code>
POST http://localhost:8080/depth-chart/NFL/players/addToChart </br>
[</br>
&nbsp;&nbsp;{ "position": "LWR", "playerId": 2, "positionInDepth": 1 }, </br>
&nbsp;&nbsp;{ "position": "LWR", "playerId": 3, "positionInDepth": 2 },</br>
&nbsp;&nbsp;{ "position": "LWR", "playerId": 4, "positionInDepth": 3}</br>
]
</code>

<h4>Remove Player From The Chart</h4>
<code>
DELETE http://localhost:8080/depth-chart/NFL/player/removeFromChart </br>
&nbsp;&nbsp;{ "position": "LWR", "playerId": 3 } </br>
</code>

<h4>Get Backups</h4>
<code>
GET http://localhost:8080/depth-chart/NFL/player/2/position/LWR/backups </br>
</code>

<h4>Get Full Depth</h4>
<code>
GET http://localhost:8080/depth-chart/NFL/player/fullDepth </br>
</code>

<h3>Run Test</h3>
<code>
mvn test
</code>