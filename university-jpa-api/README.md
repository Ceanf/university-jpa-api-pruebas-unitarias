# University API (Department / Lecturer / Course / Student — JPA + H2)

Matches your diagram:

```
        Department
        /        \
  Lecturers      Students
       \            /
        Courses ----
```

- **Department** 1 ──── * **Lecturer**
- **Department** 1 ──── * **Student**
- **Lecturer** 1 ──── * **Course** (teaches)
- **Course** * ──── * **Student** (enrollment, join table `course_student`)

## Arrays + JPA together

JPA's `@OneToMany` / `@ManyToMany` mappings must be backed by `List` or `Set` —
Hibernate wraps them in a persistent collection proxy for lazy loading and
dirty checking, so plain arrays aren't supported there. The four entities
(`Department`, `Lecturer`, `Course`, `Student`) use `List`/`Set` for that
reason.

Everywhere else — service return types, response DTOs, controller return
types — uses **arrays**: `DepartmentResponse[]`, `LecturerSummary[]`,
`CourseSummary[]`, `StudentSummary[]`. The services convert the JPA
collections to arrays with `.toArray(...)` / `Stream.toArray(...)` before
anything is returned.

## Running it

Requires Java 17 and Maven.

```bash
cd university-jpa-api
mvn spring-boot:run
```

- API: `http://localhost:8080`
- H2 console: `http://localhost:8080/h2-console`
  (JDBC URL: `jdbc:h2:mem:universitydb`, user `sa`, no password)

In IntelliJ: File → Open → select `university-jpa-api`, let Maven import,
set Project SDK to Java 17.

## Endpoints

### Departments
| Method | Path | Body | Description |
|---|---|---|---|
| POST | `/api/departments` | `{"name": "..."}` | Create a department |
| GET | `/api/departments` | | List all |
| GET | `/api/departments/{id}` | | Get one |
| DELETE | `/api/departments/{id}` | | Delete |
| POST | `/api/departments/{deptId}/lecturers/{lecturerId}` | | Assign lecturer to department |
| POST | `/api/departments/{deptId}/students/{studentId}` | | Assign student to department |

### Lecturers
| Method | Path | Body | Description |
|---|---|---|---|
| POST | `/api/lecturers` | `{"name": "..."}` | Create a lecturer |
| GET | `/api/lecturers` | | List all |
| GET | `/api/lecturers/{id}` | | Get one |
| DELETE | `/api/lecturers/{id}` | | Delete |
| POST | `/api/lecturers/{lecturerId}/courses/{courseId}` | | Assign lecturer to teach a course |

### Courses
| Method | Path | Body | Description |
|---|---|---|---|
| POST | `/api/courses` | `{"title": "...", "code": "..."}` | Create a course |
| GET | `/api/courses` | | List all |
| GET | `/api/courses/{id}` | | Get one |
| DELETE | `/api/courses/{id}` | | Delete |
| POST | `/api/courses/{courseId}/students/{studentId}` | | Enroll a student |

### Students
| Method | Path | Body | Description |
|---|---|---|---|
| POST | `/api/students` | `{"name": "..."}` | Create a student |
| GET | `/api/students` | | List all |
| GET | `/api/students/{id}` | | Get one |
| DELETE | `/api/students/{id}` | | Delete |

## Example flow

1. `POST /api/departments` `{"name": "Computer Science"}` → id 1
2. `POST /api/lecturers` `{"name": "Dr. Adebayo"}` → id 1
3. `POST /api/students` `{"name": "Khalif"}` → id 1
4. `POST /api/courses` `{"title": "Data Structures", "code": "CSC201"}` → id 1
5. `POST /api/departments/1/lecturers/1`
6. `POST /api/departments/1/students/1`
7. `POST /api/lecturers/1/courses/1`
8. `POST /api/courses/1/students/1`
9. `GET /api/departments/1` → `lecturers: [...]`, `students: [...]` as arrays
