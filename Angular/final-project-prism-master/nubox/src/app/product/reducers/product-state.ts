import { Taxonomy } from './../../core/models/taxonomy';
import { Taxon } from './../../core/models/taxon';
import { Product } from './../../core/models/product';
import { Map, Record, List } from 'immutable';

export interface ProductState extends Map<string, any> {
  productIds: List<number>;
  productEntities: Map<number, Product>;
  selectedProductId: number;
  selectedProduct: Product;
  taxonomies: List<Taxonomy>;
}

export const ProductStateRecord = Record({
  productIds: List([]),
  productEntities: Map({}),
  selectedProductId: null,
  selectedProduct: Map({}),
  taxonomies: List([])
});
