/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * This file is part of Beaver Parser Generator.                       *
 * Copyright (C) 2003,2004 Alexander Demenchuk <alder@softanvil.com>.  *
 * All rights reserved.                                                *
 * See the file "LICENSE" for the terms and conditions for copying,    *
 * distribution and modification of Beaver.                            *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

package beaver.spec;

import java.util.Comparator;

/**
 * Represents symbols of the grammar.
 */
public class GrammarSymbol {
    /** This symbol's ID */
    public short id;

    /** Name of the symbol */
    public final String name;

    /** The type of data held by this symbol. */
    public String type;

    /** Number of times this symbol is referenced in productions */
    public int nrefs;

    public

     GrammarSymbol(String name)
     {
        this.name = name;
    }

    public

     GrammarSymbol(String name, String type)
     {
        this.name = name;
        this.type = type;
    }

    public static final Comparator NUMBER_OF_REFERENCES_COMPARATOR = new Comparator(){
        public final int compare(Object sym1, Object sym2)
         {
            return ((GrammarSymbol) sym2).nrefs - ((GrammarSymbol) sym1).nrefs;
        }
    };
}
