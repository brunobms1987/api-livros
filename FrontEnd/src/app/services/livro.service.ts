import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Livro } from '../models/livro.model';

@Injectable({ providedIn: 'root' })
export class LivroService {
  private api = 'http://localhost:8080/api/livros';

  constructor(private http: HttpClient) {}

  listar(): Observable<Livro[]> {
    return this.http.get<Livro[]>(this.api);
  }

  buscarPorId(id: number): Observable<Livro> {
    return this.http.get<Livro>(`${this.api}/${id}`);
  }

  salvar(entity: any): Observable<any> {
    if (entity.id && entity.id > 0) {
      return this.http.put<any>(`${this.api}/${entity.id}`, entity);
    } else {
      const { id, ...entitySemId } = entity;
      return this.http.post<any>(this.api, entitySemId);
    }
  }

  atualizar(id: number, livro: Livro): Observable<Livro> {
    return this.http.put<Livro>(`${this.api}/${id}`, livro);
  }

  deletar(id: number): Observable<void> {
    return this.http.delete<void>(`${this.api}/${id}`);
  }
}