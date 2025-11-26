import { Assunto } from "./assunto.model";
import { Autor } from "./autor.model";

export interface Livro {
  id?: number;
  titulo: string;
  editora?: string | null;
  edicao?: number | null;
  anoPublicacao?: string | null;
  valor: number;
  autores: Autor[];
  assuntos: Assunto[];
}