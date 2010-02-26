
function problem85()

	function qua(m,n)
		local sum=0
		for i = m, 1, -1 do
			for j = n, 1, -1 do
				sum = sum + i*j
			end
		end
		return sum
	end
	
	function qua1(m,n)
		return math.floor( m*(m+1)*n*(n+1) / 4 )
	end
	
	local min,mm,mn = 100000,1,1
	for m = 1, 54 do
		local n1 = math.floor( math.sqrt(2000000*4/m/(m+1) ) ) - 1
		for n = n1, n1+2 do 
			local q = qua1(m,n)
			local diff = math.abs( 2000000 - q )
			if diff < min then
				min,mm,mn = diff, m, n
				print( m,n, q, diff )
			end
		end
	end
	
	-- for m = 50, 60 do
		-- for n = m, 60 do
			-- local q = qua(m,n)
			-- local q1 = qua1(m,n)
			-- if q ~= q1 then print( 'not equal', q, q1 ) end
			-- local diff = math.abs( 2000000 - q )
			-- if diff < min then
				-- min,mm,mn = diff, m, n
				-- --print( m,n, q, diff )
			-- end
		-- end
	-- end

	return mm, mn, mm*mn
end

-- print( math.sqrt( math.sqrt(8000000) ) )
print( 'problem85:', problem85() )
-- 52	53	2756		1971918	28082
-- problem85:	36	77	2772 1999998	2
