/*
now: 91 out of 280	(9 more until next level)
level: 2

121
http://projecteuler.net/index.php?section=scores&level=2&page=2

17th in Belarus   // next 100 - 109
6th in Clojure   // next 94 102 
*/


function problem75_not_done()
{
var limit = 1500000;
var max = limit/2;
var sqrt = Math.sqrt( max );

var s2 = [], o2 = {};
for ( var i = 0; i < sqrt; i++ )
{
  var i2 = i*i;
   o2[i2] = i;
   s2[i] = i2;
}

var ps = {};
for ( var a = 1; a < s2.length; a++ )
{
  for ( var b = a; b < s2.length; b++ )
  {
    var c2 = s2[a] + s2[b];
    var c = o2[c2];
    if ( c )
    {
      console.log( a, b, c, ":", p );
      var p = a + b + c;
      if ( !ps[p] )
        ps[p] = {a:a, b:b, c:c, p:p};
      else if ( ps[p] != "more" )
        ps[p] != "more";
    }
    if ( a + b + b >= limit )
      break;
  }
}

var count = 0;
for ( var p in ps )
{
  if ( ps[p] != "more" )
  {
    console.log( ps[p] );
    count++;
  }
}

console.log( "count", count );
}


/***************************************************************/

function problem(input)
{
  return problemXXX(input)
}


function $(id) { return document.getElementById(id) }

function log( message )
{
  $("output").innerHTML += "<br>" + message;
}

function show()
{
  $("output").innerHTML = "";

  var input = $("input").value;

  var res = problem( +input );

  log( res );
}

