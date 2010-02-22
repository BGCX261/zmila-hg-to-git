/*
just 525 of the numbers below one-thousand are bouncy. 

We shall call a positive integer that is neither increasing nor decreasing a "bouncy" number; 
for example, 155349.

As n increases, the proportion of bouncy numbers below n increases such that 
there are only 12951 numbers below one-million that are not bouncy 
and only 277032 non-bouncy numbers below 10^(10).

How many numbers below a googol (10^(100)) are not bouncy?
*/

function next_inc( prev )
{
	var next = []
	next[9] = prev[9]
	for ( var i = 8; i >= 0; i-- )
	{
		next[i] = next[i+1] + prev[i];
	}
	return next;
}

function next_dec( prev )
{
	var next = []
	next[0] = prev[0]
	for ( var i = 1; i <= 9; i++ )
	{
		next[i] = next[i-1] + prev[i];
	}
	return next;
}

function sum_state( arr, start )
{
	var res = 0;
	for (var i=start; i<arr.length; i++) 
	{
		res += arr[i];
	}
	return res - 9;
}

	

function problem113(input)
{
	var limit = +input || 6;
	var inc = [0, 1, 1, 1, 1, 1, 1, 1, 1, 1];
	var dec = [1, 1, 1, 1, 1, 1, 1, 1, 1, 0];

	var curr = 1;
	var sum = 0;
	var si = sum_state( inc, 1 );
	var sd = sum_state( dec, 0 );
	sum += si + sd + 9;
	console.log( curr, si, sd, sum );
	while ( curr < limit )
	{
		curr++;
		inc = next_inc(inc);
		dec = next_dec(dec);
		console.log( "inc", inc );
		console.log( "dec", dec );
		si = sum_state( inc, 1 );
		sd = sum_state( dec, 0 );
		sum += si + sd + 9;
		console.log( curr, si, sd, sum );
	}
	return sum;	// 51161058134250
}
