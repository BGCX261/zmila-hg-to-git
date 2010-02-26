function Sieve_of_Eratosthenes(limit)
	limit = limit or 1000000;
	local halfLimit = limit/2;
	local sqrtLimit = math.sqrt(limit) / 2;
	local era = {};
	for p = 0, halfLimit do
	    era[p] = true; -- not checked;
	end

	era[0] = false; -- 1 is not prime

	for i = 1, sqrtLimit do
		if era[i] then
			local prime = 2*i+1
			local next = math.floor( (prime*prime-1)/2 )
			for j = next, halfLimit, prime do
				era[j] = false
			end
		end
	end

	-- era = sieve: if era(i) == true then i*2+1 is prime
	return era
end

-- @return array of primes: [2,3,5,7, ...]
function buildPrimesList( sieve )
	local primes = {}
	table.insert(primes, 2)
	for i,v in pairs(sieve) do
		if v then
			table.insert(primes, 2*i+1)
		end
	end

	table.sort( primes )
	return primes
end

function buildPrimesMap( sieve )
	local primes = {}
	primes[2]=true
	for i,v in pairs(sieve) do
		if v then
			primes[2*i+1]=true
		end
	end

	return primes
end

function isPrime( era, prime )
	return era[ math.floor(prime-1)/2 ]
end

function problem49()

	local era = Sieve_of_Eratosthenes(10000)
	local soe = buildPrimesList(era)


	local ind = 1
	while soe[ind] < 1000 do
		ind = ind + 1
	end
	-- now ind = index of first prime > 1000

	function arePermutations( a, b )
		local function digits( a )
			local as = {}
			while a > 0 do
				local d = a % 10
				a = math.floor(a / 10)
				as[d] = as[d] and as[d] + 1 or 1
			end
			return as
		end

		local da = digits(a)
		local db = digits(b)

		for i,v in pairs( da ) do
			if db[i] ~= v then return false end
		end
		return true
	end

	local len = #soe

	for a = ind, len-2 do
		local A = soe[a]
		for b = a+1, len-1 do
			local B = soe[b]
			if arePermutations(A,B) then

				local diff = B-A
				local C = B + diff
				if isPrime( era, C ) and arePermutations(A,C) then
					print( A, B, C, A..B..C )
				end
			end
		end
	end

end

--~ problem49()


--[[
The primes 3, 7, 109, and 673, are quite remarkable. By taking any two primes and concatenating them
in any order the result will always be prime. For example, taking 7 and 109, both 7109 and 1097 are prime.
The sum of these four primes, 792, represents the lowest sum for a set of four primes with this property.

Find the lowest sum for a set of five primes for which any two primes concatenate to produce another prime.
--]]
function problem60()

 	local sieve = Sieve_of_Eratosthenes(10000000)
	local primes = buildPrimesList(sieve)


	local len = #primes
	local maxP = primes[len]
	print( len,  maxP )

	local function areConcatsPrime( a, b )
		return isPrime( sieve, 0+(a..b) ) and isPrime( sieve, 0+(b..a) )
	end

--[[
	for a1 = 1, len-4 do
		local A1 = primes[a1]
		for a2 = a1+1, len-3 do
			local A2 = primes[a2]
			if areConcatsPrime(A1, A2) then
			for a3 = a2+1, len-2 do
				local A3 = primes[a3]
					if areConcatsPrime(A1, A3) and areConcatsPrime(A2, A3) then
						--print( '     a3: ', a3, A3 )
						for a4 = a3+1, len-1 do
							local A4 = primes[a4]
							if areConcatsPrime(A1, A4) and areConcatsPrime(A2, A4)
									and areConcatsPrime(A3, A4) then
								print( '--- 4 ---', A1, A2, A3, A4, '#', a1,a2,a3,a4 )
--~ 								for a5 = a4+1, len do
--~ 									local A5 = primes[a5]
--~ 									if areConcatsPrime(A1, A5) and areConcatsPrime(A2, A5)
--~ 											and areConcatsPrime(A3, A5) and areConcatsPrime(A4, A5) then
--~ 										print( A1, A2, A3, A4, A5, 'sum=', A1+A2+A3+A4+A5 )
--~ 										break
--~ 									end
--~ 									if 0+(A5..A4) > maxP or 0+(A4..A5) > maxP then break end
--~ 								end
							end
							if 0+(A3..A4) > maxP or 0+(A4..A3) > maxP then break end
						end
					end
				end
			end
		end
	end
--]]


local function calcIsPrime( sieve, primes, prime )
	if prime < maxP then return isPrime( sieve, prime ) end
	for i = 1, #primes do
		if prime % primes[i] == 0 then return false end
	end
	return true
end

local function areConcatsPrimeCalc( a, b )
	return calcIsPrime( sieve, primes, 0+(a..b) ) and calcIsPrime( sieve, primes, 0+(b..a) )
end


local listOf4s = {
	{3, 7, 109, 673},
	{3, 7, 541, 4159},
	{3,11,701,8747},
	{3,	17,	449,	2069},
	{3,	17,	449,	6353},
	{3,	17,	449,	6599},
	{3,	37,	67	, 2377},
	{3,	37,	67	, 5923},
	{3,	331,	739,	8431},
	{3,	467,	617,	4253}
}

for i = 1, #listOf4s do
	local A1,A2,A3,A4 = unpack( listOf4s[i] )
	print( A1, A2, A3, A4 )

	for a5 = 100, len do
		local A5 = primes[a5]
 		if areConcatsPrimeCalc(A4, A5) and areConcatsPrimeCalc(A3, A5)
--~ 				 and areConcatsPrimeCalc(A1, A5) and areConcatsPrimeCalc(A2, A5)
		then
 			print( A1, A2, A3, A4, A5, '('..a5..') sum=', A1+A2+A3+A4+A5 )
 		end
	end
end
print( 'end' )

end

problem60()
