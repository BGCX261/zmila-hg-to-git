function problem61()

function Triangle(n) return math.floor( n*(n+1)/2 ) end
function Square(n) return n*n end
function Pentagonal(n) return math.floor( n*(3*n-1)/2 ) end
function Hexagonal(n) return n*(2*n-1) end
function Heptagonal(n) return math.floor( n*(5*n-3)/2 ) end
function Octagonal(n) return n*(3*n-2) end

function fill4( poly )

	local function add( arr, n )
		local hi = math.floor( n / 100 )
		local lo = n % 100

		-- no hi less then 10, so no need to store los
		if lo < 10 then return end

		if not arr[hi] then arr[hi] = {} end
		table.insert(arr[hi], lo)
	end

	local ret={}
	local i = 1
	local n
	repeat
		n = poly(i)
		i = i + 1
	until n > 1000

	while n < 10000 do
		add( ret, n )
		i = i + 1
		n = poly(i)
	end

	return ret
end

local p3 = fill4( Triangle )
local p4 = fill4( Square )
local p5 = fill4( Pentagonal )
local p6 = fill4( Hexagonal )
local p7 = fill4( Heptagonal )
local p8 = fill4( Octagonal )


function dumpPoly( poly )
	for hi,los in pairs(poly) do
		io.write( hi..' --> ' )
		for i = 1, #los do
			local lo = los[i]
			io.write( lo..' ' )
		end
		io.write( '\t' )
	end
end

function poly2matrix( poly, polyId, matrix )

	for hi,los in pairs(poly) do
		if not matrix[hi] then matrix[hi] = {} end
		local row = matrix[hi]
		for i = 1, #los do
			local lo = los[i]
			if not row[lo] then row[lo] = {} end
			table.insert( row[lo], polyId )
		end
	end
end

function dumpMatrix( matrix )

	for rowKey,rows in pairs(matrix) do
		io.write( '['..rowKey..'] ' )

		for cell,ids in pairs( rows ) do
			io.write( cell..': ' )
			for i,id in ipairs( ids ) do
				io.write( id, ' ' )
			end
			io.write( '\t' )
		end
		print()
	end

end

-- matrix[from][to] == { 'p4', 'p6' }: p4 and p6 have links from-to
local matrix = {}

poly2matrix( p3, 'p3', matrix )
poly2matrix( p4, 'p4', matrix )
poly2matrix( p5, 'p5', matrix )
poly2matrix( p6, 'p6', matrix )
poly2matrix( p7, 'p7', matrix )
poly2matrix( p8, 'p8', matrix )

--dumpMatrix(matrix)

function existsPidInSteps( pid, steps )
	if steps then
		local step = steps[1]
		if step.pid == pid then return true
		else return existsPidInSteps(pid, steps[2]) end
	else
		return false -- TODO
	end
end

function search( starthi, steps, hi, level )
	if level == 6 and starthi == hi then return steps end
	local row = matrix[hi]
	for lo,pids in pairs( row ) do
		for i,pid in ipairs( pids ) do
			if not existsPidInSteps( pid, steps ) then
				local step = {hi=hi, lo=lo, pid=pid}
				local res = search( starthi, {step, steps}, lo, level+1 )
				if res then return res end
			end
		end
	end
end

function find()
	for hi,row in pairs(matrix) do
		for lo,pids in pairs( row ) do
			for i,pid in ipairs( pids ) do
				local step = {hi=hi, lo=lo, pid=pid}
				local res = search( hi, {step}, lo, 1 )
				if res then return res end
			end
		end
	end
end

function showSteps( steps )
	if steps then
		local step = steps[1]
		print( step.hi, step.lo, step.pid )
		showSteps( steps[2] )
	end
end

local res = find()
if res then showSteps( res ) else print( 'not found' ) end

--[[
25	12	p7
56	25	p4
82	56	p3
28	82	p5
81	28	p6
12	81	p8

sum=28684
--]]

end --problem61

problem61()
