/*
now: 87 out of 278	(13 more until next level)
level: 2

204
http://projecteuler.net/index.php?section=scores&level=2&page=3

17th in Belarus   // next 100 - 109
6th in Clojure   // next 94 102 
9th in ECMAScript // next 86
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



/*
We shall say that an n-digit number is pandigital if it makes use of all the digits 1 to n exactly once. For example, 2143 is a 4-digit pandigital and is also prime.
What is the largest n-digit pandigital prime that exists?
*/
function problemXX_not_done(input)
{
var primes = Sieve_of_Eratosthenes(10000);

function isPandigital(n)
{
    var s = "" + n;
    var len = s.length;
    var count = 0;
    var digits = {};
    for ( var i = 0; i < len; i++ )
    {
        var c = s.charAt(i);
        if ( c == '0' ) return false;
        if ( digits[c] ) return false;
        digits[c] = true;
        count++;
    }
    return count == len;
}


console.log( "primes done" );

var maxP = 0;
for ( var i = primes.length-1; i >= 0; i-- )
{
    if ( isPandigital(primes[i]) )
    {
        maxP = i;
        break;
        //console.log( maxP );
    }
}

console.log( "max", primes[maxP] );

  return primes[maxP];
}


/*
factorials:
0=0 1=1 2=2 3=6 4=24 5=120 6=720 7=5040 8=40320 9=362880
*/
function problem34_not_done()
{

  function fact(n)
  {
      var res = 1;
      for ( var i = 1; i <= n; i++ )
          res *= i;
      return res;
  }

  var factorials = {0:0};
  for ( var i = 1; i < 10; i++ )
  {
      factorials[i] = fact( i )
  }

  function isCurious(n)
  {
      var s = 0, sn = "" + n;

      for ( var i = 0; i < sn.length; i++ )
      {
          var d = +sn.charAt(i);
          if ( d != null )
              s += factorials[d];
      }
      return s;
  }

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

