/*
now: 78 out of 277	(22 more until next level)
level: 2

385
http://projecteuler.net/index.php?section=scores&level=2&page=4

11th in Clojure   // next 81
17th in Belarus   // next 100 - 109
9th in ECMAScript // next 86
*/

/*
Problem 179
Find the number of integers 1 < n < 10^(7), for which n and n + 1 have the same number of positive divisors. For example, 14 has the positive divisors 1, 2, 7, 14 while 15 has 1, 3, 5, 15.
*/

/*
Problem 62
The cube, 41063625 (345^(3)), can be permuted to produce two other cubes: 56623104 (384^(3)) and 66430125 (405^(3)). In fact, 41063625 is the smallest cube which has exactly three permutations of its digits which are also cube.
Find the smallest cube for which exactly five permutations of its digits are cube.
*/

/*
Problem 43
The number, 1406357289, is a 0 to 9 pandigital number because it is made up of each of the digits 0 to 9 in some order, but it also has a rather interesting sub-string divisibility property.
Let d_(1) be the 1^(st) digit, d_(2) be the 2^(nd) digit, and so on. In this way, we note the following:
    * d_(2)d_(3)d_(4)=406 is divisible by 2
    * d_(3)d_(4)d_(5)=063 is divisible by 3
    * d_(4)d_(5)d_(6)=635 is divisible by 5
    * d_(5)d_(6)d_(7)=357 is divisible by 7
    * d_(6)d_(7)d_(8)=572 is divisible by 11
    * d_(7)d_(8)d_(9)=728 is divisible by 13
    * d_(8)d_(9)d_(10)=289 is divisible by 17
Find the sum of all 0 to 9 pandigital numbers with this property.
*/

/*
Problem 187
A composite is a number containing at least two prime factors. For example, 15 = 3 ? 5; 9 = 3 ? 3; 12 = 2 ? 2 ? 3.
There are ten composites below thirty containing precisely two, not necessarily distinct, prime factors: 4, 6, 9, 10, 14, 15, 21, 22, 25, 26.
How many composite integers, n < 10^(8), have precisely two, not necessarily distinct, prime factors?
*/


/*
----------------------
Surprisingly there are only three numbers that can be written as the sum of fourth powers of their digits:
    1634 = 1^(4) + 6^(4) + 3^(4) + 4^(4)
    8208 = 8^(4) + 2^(4) + 0^(4) + 8^(4)
    9474 = 9^(4) + 4^(4) + 7^(4) + 4^(4)
As 1 = 1^(4) is not a sum it is not included.
The sum of these numbers is 1634 + 8208 + 9474 = 19316.
Find the sum of all the numbers that can be written as the sum of fifth powers of their digits

fifth:
0=0 1=1 2=32 3=243 4=1024 5=3125 6=7776 7=16807 8=32768 9=59049

----------------------
145 is a curious number, as 1! + 4! + 5! = 1 + 24 + 120 = 145.
Find the sum of all numbers which are equal to the sum of the factorial of their digits.
Note: as 1! = 1 and 2! = 2 are not sums they are not included.

factorials:
0=0 1=1 2=2 3=6 4=24 5=120 6=720 7=5040 8=40320 9=362880

----------------------
Let d(n) be defined as the sum of proper divisors of n (numbers less than n which divide evenly into n).
If d(a) = b and d(b) = a, where a ? b, then a and b are an amicable pair and each of a and b are called amicable numbers.
For example, the proper divisors of 220 are 1, 2, 4, 5, 10, 11, 20, 22, 44, 55 and 110; therefore d(220) = 284. The proper divisors of 284 are 1, 2, 4, 71 and 142; so d(284) = 220.
Evaluate the sum of all the amicable numbers under 10000.
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
145 is a curious number, as 1! + 4! + 5! = 1 + 24 + 120 = 145.
Find the sum of all numbers which are equal to the sum of the factorial of their digits.
Note: as 1! = 1 and 2! = 2 are not sums they are not included.

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

