function [sorted, res] = countingSort(sorted)
 
    minElem = min(sorted);
    maxElem = max(sorted);
 
    count = zeros((maxElem-minElem+1),1);
 
    for number = sorted
        count(number - minElem + 1) = count(number - minElem + 1) + 1;
    end
 
    z = 1;
 
    for i = (minElem:maxElem)     
        while( count(i-minElem +1) > 0)
            sorted(z) = i;
            z = z+1;
            count(i - minElem + 1) = count(i - minElem + 1) - 1;
        end
    end
    
    
    listEnd = zeros(1, maxElem-minElem+1);
   for i = minElem:maxElem
       listEnd(i-minElem+1) = length(find(sorted==i));
   end
    
   res = listEnd;
 
end 
