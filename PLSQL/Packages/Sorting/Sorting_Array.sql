CREATE OR REPLACE PACKAGE sorting AS --deklaracija
    SUBTYPE array_element IS NUMBER(3);
    TYPE array_type IS TABLE OF array_element INDEX BY PLS_INTEGER;
    TYPE results_type IS RECORD
    (
    arrayValue array_element,
    iterations NUMBER(3)
    );
    TYPE search_results IS TABLE OF results_type;
    
    FUNCTION generate_array(array_size IN PLS_INTEGER) RETURN array_type;
    PROCEDURE heap_sort(p_array IN OUT array_type);
    PROCEDURE heapify(p_array IN OUT array_type, p_start IN array_element, p_end IN array_element);
    FUNCTION linear_search(p_array IN array_type, p_value IN NUMBER) RETURN NUMBER;
    FUNCTION binary_search(p_array IN array_type, p_value IN NUMBER) RETURN NUMBER;
    FUNCTION find_avg_iterations(p_array IN search_results) RETURN NUMBER;
    PROCEDURE print_array(p_array IN array_type);
    PROCEDURE print_search_results(p_array IN search_results);
    
END sorting;    