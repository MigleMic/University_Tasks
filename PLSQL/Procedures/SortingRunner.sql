SET SERVEROUTPUT ON;
SPOOL "C:\Users\anazt\AppData\Roaming\SQL Developer\Antros_uzd_rez.txt";
/
CREATE OR REPLACE PROCEDURE SortingRunner (arraySize PLS_INTEGER) IS
    l_array sorting.array_type;
    l_unsorted sorting.array_type;
    l_linear sorting.search_results := sorting.search_results();
    l_binary sorting.search_results := sorting.search_results();
    l_linear_avg NUMBER;
    l_binary_avg NUMBER;
BEGIN 
    l_array := sorting.generate_array(arraySize);
    
    l_unsorted := l_array;
 
    dbms_output.put_line('Pradinis masyvas:');
    sorting.print_array(l_unsorted);
    
    sorting.heap_sort(l_array);

    dbms_output.put_line(chr(10) || 'Surusiuotas masyvas:');
    sorting.print_array(l_array);
    
    
    
END SortingRunner;