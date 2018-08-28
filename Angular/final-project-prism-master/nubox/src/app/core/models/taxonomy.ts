import { Taxon } from './taxon';
// Taxonomy model
export interface Taxonomy {
  id: number;
  name: string;
  root: Taxon;
}
