import { Film } from './film';
import { Sala } from './sala';

export class Rezervacija {
  id!: number;
  brojOsoba!: number;
  cenaKarti!: number;
  datum!: Date;
  placeno!: boolean;
  film!: Film;
  sala!: Sala;
}
