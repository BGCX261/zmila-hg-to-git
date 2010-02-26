--[[
x0 = y0 = 0
Given that 0 <= x_(1), y_(1), x_(2), y_(2) <= 50, how many right triangles can be formed?
-- problem91: 14234
--]]
function problem91(N)

	local sum = 0

	for x1 = 0, N do 
		for y1 = 0, N do 
			for x2 = x1, N do 
				for y2 = 0, y1 do 
					local dx,dy = x2-x1,y1-y2
					local a2 = dx*dx + dy*dy
					if a2 == 0 then break end
					local b2 = x1*x1 + y1*y1
					if b2 == 0 then break end
					local c2 = x2*x2 + y2*y2
					if c2 == 0 then break end
					if a2 + b2 == c2  or  a2 + c2 == b2  or  b2 + c2 == a2 then
						sum = sum + 1
					end					
				end
			end
		end
	end
	
	return sum
end

local n = 50
print( n, ': ', problem91(n) )
-- for n = 1, 10 do
	-- print( n, ': ', problem91(n) )
-- end


--[[
http://www.research.att.com/~njas/sequences/A155154
n	 	a(n)
0		0
1		3
2		14
3		33
4		62
5		101
6		148
7		207
8		276
9		353
--]]