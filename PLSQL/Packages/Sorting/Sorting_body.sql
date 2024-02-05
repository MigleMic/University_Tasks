CREATE OR REPLACE PACKAGE BODY sorting AS

  FUNCTION generate_array(array_size IN PLS_INTEGER) RETURN array_type IS
    l_array array_type := array_type();
    BEGIN
    
     --IF array_size < 0 THEN RAISE EXCEPTIONS_HANDLING.negative_parameter;
     --END IF;
     --IF array_size = 0 THEN RAISE EXCEPTIONS_HANDLING.zero_parameter;
     --END IF; 
     
     FOR i IN 1..array_size LOOP
        l_array(i) := dbms_random.value(1, 1000);
     END LOOP;
     RETURN l_array;
     
     --EXCEPTION 
       -- WHEN EXCEPTIONS_HANDLING.negative_parameter THEN
        --LOGGER.WRITE_LOG('Ivestas neigiamas skaicius generuojant masyva',1);
        --WHEN EXCEPTIONS_HANDLING.zero_parameter THEN
        --LOGGER.WRITE_LOG('Iveskite teigama skaiciu',1);
  END generate_array;
  
  PROCEDURE heapify(p_array IN OUT array_type, p_start IN array_element, p_end IN array_element) IS
    l_root array_element := p_start;
    l_child array_element;
    l_temp array_element;
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
    l_size array_element := p_array.LAST;
    l_temp array_element;
    PROCEDURE build_heap IS
    BEGIN
    
      --IF p_array.COUNT = 0 THEN RAISE EXCEPTIONS_HANDLING 
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
  
  FUNCTION binary_search(p_array IN array_type, p_value IN NUMBER) RETURN NUMBER IS
   l_iterations NUMBER := 0;
   l_left array_element := p_array.FIRST;
   l_right array_element := p_array.LAST;
   l_mid array_element;
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
  
  FUNCTION find_avg_iterations(p_array IN search_results) RETURN NUMBER IS
   l_size array_element := p_array.LAST;
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
END sorting;    