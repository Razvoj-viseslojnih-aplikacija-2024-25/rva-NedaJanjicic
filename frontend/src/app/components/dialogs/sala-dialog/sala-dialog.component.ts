import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef, MatDialogModule } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { SalaService } from '../../../services/sala-service';
import { Sala } from '../../../models/sala';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatButtonModule } from '@angular/material/button';
import { MatInputModule } from '@angular/material/input';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { MatSelectModule } from '@angular/material/select';
import { BioskopService } from '../../../services/bioskop-service';
import { Bioskop } from '../../../models/bioskop';

@Component({
  selector: 'app-sala-dialog',
  standalone: true,
  imports: [MatDialogModule, MatFormFieldModule, MatButtonModule, MatInputModule, FormsModule, CommonModule, MatSelectModule],
  templateUrl: './sala-dialog.component.html',
  styleUrls: ['./sala-dialog.component.css']
})
export class SalaDialog implements OnInit {
  flag!: number;
  bioskopi!: Bioskop[];

  constructor(
    private snackBar: MatSnackBar,
    private dialogRef: MatDialogRef<SalaDialog>,
    private salaService: SalaService,
    private bioskopService: BioskopService,
    @Inject(MAT_DIALOG_DATA) public data: Sala
  ) {}

  ngOnInit(): void {
    this.bioskopService.getAllBioskops().subscribe((data) => (this.bioskopi = data));
  }

  add(): void {
    this.salaService.createSala(this.data).subscribe({
      next: (result) => {
        this.dialogRef.close(1);
        this.snackBar.open(`Sala ID ${result.id} uspešno dodata!`, 'OK', { duration: 2500 });
      },
      error: (err) => console.error(err),
    });
  }

  update(): void {
    this.salaService.updateSala(this.data).subscribe({
      next: (result) => {
        this.dialogRef.close(1);
        this.snackBar.open(`Sala ID ${result.id} uspešno izmenjena!`, 'OK', { duration: 2500 });
      },
      error: (err) => console.error(err),
    });
  }

  delete(): void {
    this.salaService.deleteSala(this.data.id).subscribe({
      next: (res) => {
        this.dialogRef.close(1);
        this.snackBar.open(res, 'OK', { duration: 2500 });
      },
      error: (err) => console.error(err),
    });
  }

  cancel(): void {
    this.dialogRef.close();
    this.snackBar.open('Odustali ste od izmene', 'OK', { duration: 2000 });
  }

  compare(a: Bioskop, b: Bioskop) {
    return a.id === b.id;
  }
}
