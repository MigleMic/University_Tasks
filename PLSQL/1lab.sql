SET SERVEROUTPUT ON;
SPOOL "C:\Users\anazt\AppData\Roaming\SQL Developer\Pirmos_uzd_rez.txt";
/
SET VERIFY OFF;
DECLARE
    --array 
    SUBTYPE element IS NUMBER(3);
    TYPE array_type IS TABLE OF element INDEX BY PLS_INTEGER;
    l_array array_type;
    l_unsorted array_type;
    l_array_size NUMBER(3);
    l_index NUMBER := 1;
    l_value element;

    TYPE results_type IS RECORD
    (
    arrayValue element,
    iterations NUMBER(3)
    );
    TYPE search_results IS TABLE OF results_type;
    l_linear search_results := search_results();
    l_binary search_results := search_results();
    l_linear_avg NUMBER;
    l_binary_avg NUMBER;
   
  PROCEDURE heapify(p_array IN OUT array_type, p_start IN element, p_end IN element) IS
    l_root element := p_start;
    l_child element;
    l_temp element;
  BEGIN
    l_child := 2 * l_root;

    WHILE l_child <= p_end LOOP
      IF (l_child < p_end) AND (p_array(l_child) < p_array(l_child + 1)) THEN
        l_child := l_child + 1;
      END IF;

      IF p_array(l_root) < p_array(l_child) THEN
        l_temp := p_array(l_root);
        p_array(l_root) := p_array(l_child);
        p_array(l_child) := l_temp;

        l_root := l_child;
        l_child := 2 * l_root;
      ELSE
        RETURN;
      END IF;
    END LOOP;
  END heapify;

  PROCEDURE heap_sort(p_array IN OUT array_type) IS
    l_size element := p_array.LAST;
    l_temp element;
    PROCEDURE build_heap IS
    BEGIN
      FOR i IN REVERSE 1..(l_size/2) LOOP
        heapify(p_array, i, l_size);
      END LOOP;
    END build_heap;

  BEGIN
    build_heap;

    FOR i IN REVERSE 2..l_size LOOP
      l_temp := p_array(1);
      p_array(1) := p_array(i);
      p_array(i) := l_temp;

      heapify(p_array, 1, i-1);
    END LOOP;
  END heap_sort;

  FUNCTION linear_search(p_array IN array_type, p_value IN NUMBER) RETURN NUMBER IS
  l_iterations NUMBER := 0;
  BEGIN 
   FOR i IN p_array.FIRST .. p_array.LAST LOOP
    l_iterations := l_iterations + 1;
    
    IF (p_array(i) = p_value) THEN
     RETURN i;
    END IF;
   END LOOP;
   
   RETURN 0;
  END;  
  
  FUNCTION linear_algorithm(p_unsorted IN array_type, p_sorted IN array_type) RETURN search_results IS
  l_value element;
  l_index PLS_INTEGER;
  l_linear search_results := search_results();
  BEGIN
   FOR i IN p_unsorted.FIRST .. p_unsorted.LAST LOOP
     l_value := p_unsorted(i);
     l_index := linear_search(p_sorted, l_value);
     l_linear.EXTEND(1);
     l_linear(i).arrayValue := l_value;
     l_linear(i).iterations := l_index;
    END LOOP;
    RETURN l_linear;
  END linear_algorithm;

  FUNCTION binary_search(p_array IN array_type, p_value IN NUMBER) RETURN NUMBER IS
   l_iterations NUMBER := 0;
   l_left element := p_array.FIRST;
   l_right element := p_array.LAST;
   l_mid element;
  BEGIN
  WHILE l_left <= l_right LOOP
    l_mid := FLOOR((l_right + l_left) / 2);
    
    l_iterations := l_iterations + 1;
    
    IF(p_array(l_mid) = p_value) THEN
     RETURN l_iterations;
    ELSIF (p_array(l_mid) < p_value) THEN
     l_left := l_mid + 1;
    ELSE
     l_right := l_mid - 1;
    END IF;
   END LOOP;
   
   RETURN 0;
  END binary_search;
  
  FUNCTION binary_algorithm(p_unsorted IN array_type, p_sorted IN array_type) RETURN search_results IS
  l_value element;
  l_index PLS_INTEGER;
  l_binary search_results := search_results();
  BEGIN
   FOR i IN p_unsorted.FIRST .. p_unsorted.LAST LOOP
     l_value := p_unsorted(i);
     l_index := binary_search(p_sorted, l_value);
     l_binary.EXTEND(1);
     l_binary(i).arrayValue := l_value;
     l_binary(i).iterations := l_index;
    END LOOP;
    RETURN l_binary;
  END binary_algorithm;

  FUNCTION generate_array(array_size IN PLS_INTEGER) RETURN array_type IS
    l_array array_type := array_type();
    BEGIN
     FOR i IN 1..array_size LOOP
     l_array(i) := dbms_random.value(1, 1000);
     END LOOP;
     RETURN l_array;
  END generate_array;     
  
  FUNCTION find_avg_iterations(p_array IN search_results) RETURN NUMBER IS
   l_size element := p_array.LAST;
   l_sum NUMBER := 0;
   l_avg NUMBER;
   BEGIN
    FOR i IN p_array.FIRST .. p_array.LAST LOOP
     l_sum := l_sum + p_array(i).iterations;
    END LOOP;
    
    l_avg := FLOOR(l_sum / l_size);
    RETURN l_avg;
  END find_avg_iterations;

  PROCEDURE print_array(p_array IN array_type) IS
  BEGIN
   FOR i IN p_array.FIRST .. p_array.LAST LOOP
        dbms_output.put_line('Narys '|| i || ' reiksme - ' || p_array(i) || ' ');
    END LOOP;
  END print_array;

  PROCEDURE print_search_results(p_array IN search_results) IS
  BEGIN
   FOR i IN p_array.FIRST .. p_array.LAST LOOP
     dbms_output.put_line('Masyvo narys '|| p_array(i).arrayValue || ' iteraciju skaicius - ' || p_array(i).iterations || ' ');
    END LOOP;
  END print_search_results;  
  
  
BEGIN
    l_array_size := '&s_text';
    l_array := generate_array(l_array_size);
    
    l_unsorted := l_array;
 
    dbms_output.put_line('Pradinis masyvas:');
    print_array(l_unsorted);
    
    heap_sort(l_array);

    dbms_output.put_line(chr(10) || 'Surusiuotas masyvas:');
    print_array(l_array);

    /*<<linear_algorithm>>
    FOR i IN l_unsorted.FIRST .. l_unsorted.LAST LOOP
     l_value := l_unsorted(i);
     l_index := linear_search(l_array, l_value);
     l_linear.EXTEND(1);
     l_linear(i).arrayValue := l_value;
     l_linear(i).iterations := l_index;
    END LOOP linear_algorithm;*/
    l_linear := linear_algorithm(l_unsorted, l_array);

    dbms_output.put_line(chr(10) || 'Tiesines paieskos rezultatai:');
    print_search_results(l_linear);
    l_linear_avg := find_avg_iterations(l_linear);
    dbms_output.put_line(chr(10) || 'Vidutinis iteraciju skaicius naudojantis tiesines paieskos algoritmu : ' || l_linear_avg);
    
    /*<<binary_algorithm>>
    FOR i IN l_unsorted.FIRST .. l_unsorted.LAST LOOP
     l_value := l_unsorted(i);
     l_index := binary_search(l_array, l_value);
     l_binary.EXTEND(1);
     l_binary(i).iterations := l_index;
     l_binary(i).arrayValue := l_value;
    END LOOP binary_algorithm;*/
    
    l_binary := binary_algorithm(l_unsorted, l_array);
    dbms_output.put_line(chr(10) || 'Dvejetaines paieskos rezultatai:');
    print_search_results(l_binary);
    l_binary_avg := find_avg_iterations(l_binary);
    dbms_output.put_line(chr(10) || 'Vidutinis iteraciju skaicius naudojantis dvejetaines paieskos algoritmu : ' || l_binary_avg);
END;
/
SPOOL OFF;
SET VERIFY ON;
SET SERVEROUTPUT OFF;