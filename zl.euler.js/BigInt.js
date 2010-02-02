function BigInt( n )
{
	this.digits = [];
   if ( n )
   {
      if ( typeof n == "number" )
			this.digits = BigInt.normalize(n);
		else if ( typeof n == "string" )
			this.digits = BigInt.normalizeString(n);
		else if ( this.isArray(n) )
			this.digits = this.normalizeArray(n);
		else if ( n instanceof BigInt )
			this.digits = copy( n );
    }
}
// public static
BigInt.normalize = function(n)
{
	var digits  = [];
	while ( n > BigInt.base )
	{
		var r = n % BigInt.base;
		digits.unshift( r );
		n = Math.floor(n / BigInt.base);
	}
	digits.unshift( n );
	return digits;
}
BigInt.normalizeString = function(n)
{
	var digits  = [], len = n.length, size = BigInt.digitSize;
	while ( len > size )
	{
		var d = n.substring( len - size, len );
		digits.unshift( +d );
		n = n.substring( 0, len - size ),
		len -= size;
	}
	if ( n > 0 )
		digits.unshift( +n );
	return digits;
}
BigInt.digitSize = 7;
BigInt.base = Math.pow( 10, BigInt.digitSize );
//BigInt.base = 10000000000;
//BigInt.digitSize = 10;

BigInt.prototype = 
{
	// digits[0] - hi
	// digits[digits.length] - lo
	
	len: function()
	{
		if ( !this.digits  ||  this.digits.length == 0 )
			return 0;
		var len = this.digits.length > 0 ? (this.digits.length-1)*BigInt.digitSize : 0;
		len += ("" + this.digits[0]).length;
		return len;
	},	

	toString: function()
	{
		if ( !this.digits  ||  this.digits.length == 0 )
			return "0";
		var s = "" + this.digits[0];
		for ( var i = 1; i < this.digits.length; i++ )
		{
			s += "`" + pad10( "" + this.digits[i]) ;
		}
		return s;
		
		function pad10( s )
		{
			var len = s.length;
			while ( len < BigInt.digitSize )
			{
				s = "0" + s;
				len++;
			}
			return s;
		}
	},

  isArray: function(object) 
  {
      return object && object.constructor === Array;
  },

  normalizeArray: function(ar)
  {
		var digits  = [], over = 0;
		for ( var i = 0; i < ar.length; i++ )
		{
			var n = BigInt.normalize(ar[i] + rem);
			digits.unshift( n[1] );
			over = n.length > 1 ? n[0] : 0;
		}
		if ( over )
			digits.unshift( over );
		
		return digits;
  },

	copy: function( that )
	{
		digits = [];
		for ( var i = 0; i < that.digits.length; i++ )
		{
			digits[i] = that.digits[i];
		}
		return digits;
	},
	
  //TODO
  add1: function( that )
  {},
  
	add: function( that )
	{
		var thisD = this.digits, thatD = that.digits,
			thisL = thisD.length-1, thatL = thatD.length-1,
			over = 0, sum = new BigInt(), res = sum.digits;
			
		process( 
			function() { return thisL >= 0 && thatL >= 0; }, 
			function() 
			{ 
				var d = thisD[thisL--] + thatD[thatL--] + over; 
				return d; 
			}
		);
		process( 
			function() { return thisL >= 0; }, 
			function() { return thisD[thisL--] + over; }
			);
		process( 
			function() { return thatL >= 0; }, 
			function() { return thatD[thatL--] + over; }
			);
			
		if ( over )
			res.unshift( over );
		
		function process( cond, next )
		{
			while ( cond() )
			{
				var n = BigInt.normalize( next() );
				if ( n.length > 1 )
				{
					res.unshift( n[1] );
					over = n[0];
				}
				else
				{
					res.unshift( n[0] );
					over = 0;
				}
			}
		}
		
		return sum;
	},
	
	mul1: function( num )
	{
		var mul = new BigInt(0), res = mul.digits;
		var over = 0, d = this.digits, b = BigInt.base;
		for ( var i = d.length-1; i >= 0; i-- )
		{
			var prod = d[i] * num + over;
			res.unshift( prod % b );
			over = Math.floor(prod / b);
		}
		if ( over )
			res.unshift( over );
		
		return mul;
	}, 
	
	shift: function( num )
	{
		for ( var i = 0; i < num; i++ )
			this.digits.push( 0 );
	},
	
	mul: function( that )
	{
		var thisD = this.digits, thatD = that.digits,
		thatL = thatD.length-1,
		product = new BigInt(0), res = product.digits;
		
		for ( var i = thatL, shift = 0; i >= 0; i--, shift++ )
		{
			var p1 = this.mul1( thatD[i] );
			p1.shift( shift );
			product = product.add(p1);
		}
		
		return product;
	}, 
	
	power: function( pow )
	{
		if ( pow == 0 )
			return new BigInt(1);
		
		var res = new BigInt(1);
		for (var i = 0; i < pow; i++)
		{
			res = res.mul(this);
		}
		
		return res;
	}
}
