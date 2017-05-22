package others;

/**
 * A mozdonyok (Locomotive) váltótól (Switch) váltóig egy adott Direction szerint mozognak,
 * amik az alábbiak lehetnek. Ha a mozdony rálép egy váltóra, a váltó átadja neki az új Directiont.
 * A következő váltóig e szerint a Direction szerint fog mozogni.
 */
public enum Direction {
    PREVIOUS, NEXT, PREPRE;
}
