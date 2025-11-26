import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Assunto } from '../models/assunto.model';

@Injectable({ providedIn: 'root' })
export class AssuntoService {
  private api = 'http://localhost:8080/api/assuntos';

  constructor(private http: HttpClient) { }

  listar(): Observable<Assunto[]> {
    return this.http.get<Assunto[]>(this.api);
  }

  buscarPorId(id: number): Observable<Assunto> {
    return this.http.get<Assunto>(`${this.api}/${id}`);
  }

  salvar(entity: any): Observable<any> {
    if (entity.id && entity.id > 0) {
      const id = Number(entity.id);
      return this.http.put<any>(`${this.api}/${id}`, entity);
    } else {
      const { id, ...semId } = entity;
      return this.http.post<any>(this.api, semId);
    }
  }

  atualizar(id: number, assunto: Assunto): Observable<Assunto> {
    return this.http.put<Assunto>(`${this.api}/${id}`, assunto);
  }

  deletar(id: number): Observable<void> {
    return this.http.delete<void>(`${this.api}/${id}`);
  }
}