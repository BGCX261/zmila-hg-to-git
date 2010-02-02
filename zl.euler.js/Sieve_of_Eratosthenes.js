// return [primes, arrPrimes] = object and array
function Sieve_of_Eratosthenes(limit)
{
	
	limit = limit || 1000000;
	var halfLimit = limit/2;
	var sqrtLimit = Math.sqrt(limit) / 2;
	var era = [];
	for ( var p = 0; p < halfLimit; p++)
	{
	    era[p] = true; // not checked;
	}

	era[0] = false; // 1 is not prime
	for ( var i = 1; i < sqrtLimit; i++ )
	{
	    if ( !era[i] )
	        continue;

	    var prime = 2*i+1;
	    var next = Math.floor( (prime*prime-1)/2 );
	    for ( var j = next; j < halfLimit; j += prime )
	    {
	        era[j] = false;
	    }
	}

  var arrPrimes = [2];
	var primes = {2:true};
	for ( var p = 0; p < halfLimit; p++)
	{
		if ( era[p] )
		{
			var pr = 2*p + 1;
			arrPrimes.push(pr);
			primes[pr] = true;
		}
	}
	
	return [primes, arrPrimes];
}
